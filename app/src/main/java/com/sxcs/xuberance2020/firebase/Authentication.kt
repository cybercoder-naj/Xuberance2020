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
    var schoolCode = ""

    fun signIn(email: String, password: String, callback: (Task<AuthResult>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback(task)
                user = auth.currentUser
                user!!.email?.let {
                    schoolCode = it.substring(0, it.indexOf('@')).toUpperCase(Locale.getDefault())
                }
            }
    }

    fun signOut() {
        if (user != null)
            auth.signOut()
    }

    fun resetPassword(oldPassword: String, newPassword: String, callback: (String) -> Unit) {
        user?.let {
            it.reauthenticate(EmailAuthProvider.getCredential(it.email!!, oldPassword))
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        it.updatePassword(newPassword).addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                callback("Password successfully changed")
                            } else
                                callback("Error changing password. Please try again")
                        }
                    } else
                        callback("Error signing in. Please try again")
                }
        }
    }
}