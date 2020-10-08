package com.sxcs.xuberance2020.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

object Authentication {

    private val auth = Firebase.auth
    var user = auth.currentUser

    fun signIn(email: String, password: String, callback: (Task<AuthResult>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback(task)
                user = auth.currentUser
            }
    }

    val schoolCode: String
        get() {
            if (user != null) {
                return user!!.email?.let {
                    return@let it.substring(0, it.indexOf('@')).toUpperCase(Locale.getDefault())
                } ?: ""
            }
            return ""
        }
}