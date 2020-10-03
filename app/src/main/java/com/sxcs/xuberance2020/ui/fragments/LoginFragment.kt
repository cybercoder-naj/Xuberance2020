package com.sxcs.xuberance2020.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.Preferences
import com.sxcs.xuberance2020.databinding.FragmentLoginBinding
import com.sxcs.xuberance2020.firebase.Authentication
import com.sxcs.xuberance2020.utils.toast
import com.sxcs.xuberance2020.utils.validateXuberanceEmail

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private var listener: OnLoggedInListener? = null

    interface OnLoggedInListener {
        fun updateUser()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoggedInListener) {
            listener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)

        loadUsername(Preferences.username)

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
        }
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
                    requireContext().toast("Invalid ID or password. Please try again!")
                    binding.progressCircular.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun loadUsername(username: String) {
        binding.username.setText(username)
    }

    private fun updateUI() {
        listener?.updateUser()
    }
}