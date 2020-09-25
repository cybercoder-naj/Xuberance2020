package com.sxcs.xuberance2020.ui.activities

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.sxcs.xuberance2020.data.Preferences
import com.sxcs.xuberance2020.databinding.ActivityWelcomeBinding
import com.sxcs.xuberance2020.utils.toast

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/General")

        binding.videoViewWelcome.apply {
            // TODO add video and URI path
            val uriFirst = Uri.parse("")
            setVideoURI(uriFirst)
            setOnCompletionListener {
                if (Preferences.isFirstTime)
                    Preferences.isFirstTime = false
                finish()
            }
        }
    }

    override fun onBackPressed() {
        toast("You cannot go back from this screen")
    }
}