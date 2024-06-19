package com.team2.chitchat.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.team2.chitchat.R
import com.team2.chitchat.data.domain.model.error.ErrorModel
import com.team2.chitchat.databinding.FragmentRegistrationBinding
import com.team2.chitchat.ui.base.BaseFragment
import com.team2.chitchat.utils.setErrorBorder
import com.team2.chitchat.utils.toastLong
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(), View.OnClickListener {

    private val registrationViewModel: RegistrationViewModel by viewModels()

    override fun inflateBinding() {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
    }

    override fun createViewAfterInflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        setupListeners()
    }

    private fun setupListeners() {
        binding?.btRegister?.setOnClickListener(this)
    }

    override fun configureToolbarAndConfigScreenSections() {
        fragmentLayoutWithToolbar()
        showToolbar(title = getString(R.string.registration_title), showBack = true)
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            registrationViewModel.loadingFlow.collect {
                showLoading(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            registrationViewModel.successFlow.collect {
                requireContext().toastLong("Registro exitoso")
                findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            registrationViewModel.errorFlow.collect { error ->
                checkUser(error)
            }
        }
    }

    override fun viewCreatedAfterSetupObserverViewModel(view: View, savedInstanceState: Bundle?) =
        Unit

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btRegister -> {
                if (canDoLogin()) {
                    if (binding?.etPassword?.text.toString() == binding?.etRepeatPass?.text.toString()) {
                        registrationViewModel.postUser(
                            binding?.etUser?.text.toString(),
                            binding?.etPassword?.text.toString(),
                            binding?.etNick?.text.toString()
                        )
                    } else {
                        binding?.etRepeatPass?.setErrorBorder(requireContext())
                        requireContext().toastLong("Contraseñas no coinciden")
                    }
                } else {
                    requireContext().toastLong("Rellena todos los campos")
                }
            }
        }
    }

    private fun checkUser(error: ErrorModel) {
        if (error.message == "User exist") {
            binding?.etUser?.setErrorBorder(requireContext())
        } else {
            requireContext().toastLong(error.message)
        }
    }

    private fun canDoLogin(): Boolean {
        return binding?.etUser?.text.toString().isNotBlank() && binding?.etPassword?.text.toString()
            .isNotBlank() && binding?.etRepeatPass?.text.toString()
            .isNotBlank() && binding?.etNick?.text.toString().isNotBlank()
    }
}