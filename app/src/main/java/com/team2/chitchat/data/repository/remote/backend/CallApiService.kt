package com.team2.chitchat.data.repository.remote.backend

import com.team2.chitchat.data.repository.remote.request.chats.NewChatRequest
import com.team2.chitchat.data.repository.remote.request.users.LoginUserRequest
import com.team2.chitchat.data.repository.remote.request.users.RegisterUserRequest
import com.team2.chitchat.data.repository.remote.response.BaseResponse
import com.team2.chitchat.data.repository.remote.response.LogOutResponse
import com.team2.chitchat.data.repository.remote.response.chats.GetChatsResponse
import com.team2.chitchat.data.repository.remote.response.chats.PostNewChatResponse
import com.team2.chitchat.data.repository.remote.response.messages.GetMessagesResponse
import com.team2.chitchat.data.repository.remote.response.users.GetUserResponse
import com.team2.chitchat.data.repository.remote.response.users.PostLoginResponse
import com.team2.chitchat.data.repository.remote.response.users.PostRegisterResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallApiService @Inject constructor(private val apiService: ApiService) : BaseService() {

    //RegisterUser
    suspend fun callPostRegisterUser(registerUserRequest: RegisterUserRequest): BaseResponse<PostRegisterResponse> {
        return apiCall { apiService.postRegisterUser(registerUserRequest) }
    }

    //LoginUser
    suspend fun callPostLoginUser(loginUserRequest: LoginUserRequest): BaseResponse<PostLoginResponse> {
        return apiCall { apiService.postLoginUser(loginUserRequest) }
    }

    //ContactsList
    suspend fun callGetContactsList(): BaseResponse<ArrayList<GetUserResponse>> {
        return apiCall { apiService.getContactsList() }
    }

    //Chats
    suspend fun callGetChats(): BaseResponse<ArrayList<GetChatsResponse>> {
        return apiCall { apiService.getChats() }
    }

    suspend fun callPostNewChat(newChatRequest: NewChatRequest): BaseResponse<PostNewChatResponse> {
        return apiCall { apiService.postNewChat(newChatRequest) }
    }

    //Messages
    suspend fun callGetMessages(): BaseResponse<ArrayList<GetMessagesResponse>> {
        return apiCall { apiService.getMessages() }
    }

    // Get Profile
    suspend fun callGetProfile(): BaseResponse<GetUserResponse> {
        return apiCall { apiService.getProfile() }
    }

    suspend fun callLogout(): BaseResponse<LogOutResponse> {
        return apiCall { apiService.putLogOut() }
    }
}