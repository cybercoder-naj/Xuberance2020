package com.sxcs.xuberance2020.ui.dialogs

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.sxcs.xuberance2020.databinding.DialogSubmitBinding
import com.sxcs.xuberance2020.utils.toast

class SubmitDialog(context: Context) : AppCompatDialog(context) {

    private lateinit var binding: DialogSubmitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSubmitBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        with(binding) {
            setContentView(root)

            linearSub.setOnClickListener {
                sendToWebsite()
                dismiss()
            }

            linearCopyLink.setOnClickListener {
                copyLinkToClipboard()
                dismiss()
            }

            linearEmail.setOnClickListener {
                val sendLinkDialog = SendEmailDialog(context)
                dismiss()
                sendLinkDialog.show()
            }
        }
    }

    private fun copyLinkToClipboard() {
        val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        ClipData.newPlainText("link label", "TODO get link").also { // TODO get link
            clipboard.setPrimaryClip(it)
        }
        context.toast("Link has been copied to clipboard")
    }

    private fun sendToWebsite() {
        // TODO Create link and send them to the website.
    }
}