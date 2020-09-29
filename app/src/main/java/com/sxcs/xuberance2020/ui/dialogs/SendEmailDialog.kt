package com.sxcs.xuberance2020.ui.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.Credentials
//import com.sxcs.xuberance2020.data.Credentials
import com.sxcs.xuberance2020.databinding.DialogSendEmailBinding
import com.sxcs.xuberance2020.utils.toast
import com.sxcs.xuberance2020.utils.validateEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendEmailDialog(
    context: Context
) : AppCompatDialog(context) {

    lateinit var binding: DialogSendEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSendEmailBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        with(binding) {
            setContentView(root)

            btnConfirm.setOnClickListener {
                val email = editTextEmail.text.toString()

                if (validateEmail(email)) {
                    sendEmail(email)
                    dismiss()
                } else {
                    editTextEmail.error = context.getString(R.string.invalid_username)
                    editTextEmail.requestFocus()
                }
            }
        }
    }

    private fun sendEmail(email: String) = CoroutineScope(Dispatchers.IO).launch {
        val props = Properties().apply {
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.auth", "true")
            put("mail.smtp.port", "587")
        }

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication =
                PasswordAuthentication("xuberance20@gmail.com", Credentials.PASS)
        })

        try {
            MimeMessage(session).apply {
                setFrom(InternetAddress("xuberance20@gmail.com"))
                setRecipient(
                    Message.RecipientType.TO,
                    InternetAddress(email)
                )
                subject = "Submit your work!"

                setContent(
                    "This is your submission link\n\nhttps://script.google.com/macros/s/AKfycbzDxv14vHf2lcgKL09sKoGsVcQygL1N9sAaQyUb1a-Ykg1sNuI_/exec",
                    "text/html; charset=utf-8"
                )
            }.also {
                Transport.send(it)
            }
            withContext(Dispatchers.Main) {
                context.toast("Submission link has been sent to your email")
            }
        } catch (e: MessagingException) {
            withContext(Dispatchers.Main) {
                context.toast("Error sending email, please try again later")
            }
        }
    }
}