package com.sxcs.xuberance2020.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.View.INVISIBLE
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.Preferences
import com.sxcs.xuberance2020.databinding.FragmentSchoolProfileBinding
import com.sxcs.xuberance2020.firebase.Authentication
import com.sxcs.xuberance2020.ui.activities.PanelActivity
import com.sxcs.xuberance2020.utils.toast
import com.sxcs.xuberance2020.utils.validateXuberanceEmail

class SchoolProfileFragment : Fragment(R.layout.fragment_school_profile) {

    private lateinit var binding: FragmentSchoolProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSchoolProfileBinding.bind(view)

        if (!Preferences.loggedInFirstTime && Preferences.username != "")
            loadUsername(Preferences.username)

        setClickableTextColor()

        with(binding) {
            btnLogin.setOnClickListener {
                progressCircular.isVisible = true
                val user = username.text.toString()
                val pass = password.text.toString()

                if (!validateXuberanceEmail(user)) {
                    username.error = getString(R.string.invalid_username)
                    username.requestFocus()
                } /*else if (validatePassword(pass).isNotBlank()) {
                    password.error = validatePassword(pass)
                    password.requestFocus()
                }*/ else
                    login(user, pass)
            }

            textViewForgotPassword.setOnClickListener {

            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setClickableTextColor() {
        with(binding) {
            textViewForgotPassword.setOnTouchListener { _, event ->
                when (event.action) {
                    ACTION_DOWN -> textViewForgotPassword.setTextColor(
                        requireContext().getColor(
                            android.R.color.holo_blue_dark
                        )
                    )
                    ACTION_UP -> textViewForgotPassword.setTextColor(
                        requireContext().getColor(
                            R.color.color_sky
                        )
                    )
                }
                true
            }
        }
    }

    private fun loadUsername(username: String) {
        binding.username.setText(username)
    }

    private fun login(username: String, password: String) {
        Authentication.signIn(username, password) {
            if (it.isSuccessful) {
                updateUI(password)
                Preferences.username = username
                if (Preferences.loggedInFirstTime) {
                    Preferences.loggedInFirstTime = false
                }
            } else {
                try {
                    throw it.exception!!
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    requireContext().toast("Invalid ID or password. Please try again!")
                    binding.progressCircular.visibility = INVISIBLE
                }
            }
        }
    }

    private fun updateUI(pass: String) {
        binding.progressCircular.visibility = INVISIBLE
        PanelActivity.getIntent(requireContext()).also { intent ->
            startActivity(intent)
        }
    }

}