package com.sxcs.xuberance2020.ui.activities

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.messaging.FirebaseMessaging
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.Preferences
import com.sxcs.xuberance2020.databinding.ActivityMainBinding
import com.sxcs.xuberance2020.firebase.Authentication
import com.sxcs.xuberance2020.firebase.Database
import com.sxcs.xuberance2020.utils.moveGradient
import com.sxcs.xuberance2020.utils.toast
import com.sxcs.xuberance2020.utils.validateXuberanceEmail
import kotlin.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUsername(Preferences.username)

        moveGradient(binding.mainParentLayout.background as AnimationDrawable)

        with(binding) {
            btnLogin.setOnClickListener {
                progressCircular.isVisible = true
                val user = username.text.toString()
                val pass = password.text.toString()

                if (!validateXuberanceEmail(user)) {
                    username.error = getString(R.string.invalid_username)
                    username.requestFocus()
                    progressCircular.isVisible = false
                }  else
                    login(user, pass)
            }

            // TODO add this.
            /*textViewForgotPassword.setOnClickListener {

            }*/

            btnGuest.setOnClickListener {
                Authentication.user = null
                Intent(this@MainActivity, SectionsActivity::class.java).also {
                    startActivity(it)
                }
                finish()
            }
        }
    }

    private fun loadUsername(username: String) {
        binding.username.setText(username)
    }

    private fun login(username: String, password: String) {
        Authentication.signIn(username, password) {
            if (it.isSuccessful) {
                updateUI()
                Preferences.username = username
                Preferences.hasEverLoggedIn = true
            } else {
                try {
                    throw it.exception!!
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    toast("Invalid ID or password. Please try again!")
                    binding.progressCircular.visibility = View.INVISIBLE
                } catch (e: FirebaseAuthException) {
                    toast("Invalid ID or password. Please try again!")
                    binding.progressCircular.visibility = View.INVISIBLE
                } catch (e: Exception) {
                    toast("Error while logging in")
                    binding.progressCircular.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun updateUI() {
        toast("You have successfully logged in")
        FirebaseMessaging.getInstance().subscribeToTopic("reps")
        Intent(this, SectionsActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }
}