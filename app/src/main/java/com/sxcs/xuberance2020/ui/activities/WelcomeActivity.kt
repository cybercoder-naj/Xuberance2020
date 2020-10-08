package com.sxcs.xuberance2020.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.databinding.ActivityWelcomeBinding
import com.sxcs.xuberance2020.firebase.Authentication
import com.sxcs.xuberance2020.utils.toast

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().subscribeToTopic("general")

        binding.videoViewWelcome.apply {
            val uriFirst = Uri.parse("android.resource://$packageName/${R.raw.splash}")
            setVideoURI(uriFirst)
            start()
            setOnCompletionListener {
                updateUi()
            }
        }
    }

    private fun updateUi() {
        if (Authentication.user == null) {
            Intent(this@WelcomeActivity, MainActivity::class.java).also {
                startActivity(it)
            }
        } else if (Authentication.user != null) {
            Intent(this@WelcomeActivity, SectionsActivity::class.java).also {
                startActivity(it)
            }
        }
        finish()
    }

    override fun onBackPressed() {
        toast("You cannot go back from this screen")
    }
}