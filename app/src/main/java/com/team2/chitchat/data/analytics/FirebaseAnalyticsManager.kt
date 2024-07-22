package com.team2.chitchat.data.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsManager @Inject constructor() {

    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    fun logEventEnterScreen(nameScreen: String) {
        val bundle = Bundle()
        bundle.putString("NAME_SCREEN", nameScreen)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}