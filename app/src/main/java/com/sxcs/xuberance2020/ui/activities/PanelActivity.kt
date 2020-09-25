package com.sxcs.xuberance2020.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sxcs.xuberance2020.databinding.ActivityPanelBinding
import com.sxcs.xuberance2020.firebase.Authentication
import com.sxcs.xuberance2020.firebase.Database
import com.sxcs.xuberance2020.ui.dialogs.SubmitDialog
import com.sxcs.xuberance2020.utils.toast

class PanelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPanelBinding

    companion object {
        fun getIntent(context: Context) = Intent(context, PanelActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Authentication.user == null)
            toast("Unexpected error. Try logging in again.")
        else {
            toast("You have successfully signed in!")

            Database.hasRegistered {
                if (it)
                    binding.btnRegistration.isEnabled = false
            }

            Database.getSchoolName()?.also {
                binding.textViewName.text = it
            } ?: toast("Error getting School Name")

            binding.btnRegistration.setOnClickListener {
                AlertDialog.Builder(this)
                    .setTitle("Entering Registration Panel...")
                    .setMessage("Make sure you have all the list of the names of Participants and their valid phone numbers.")
                    .setPositiveButton("Continue") { dialog, _ ->
                        RegistrationActivity.getIntent(this@PanelActivity).also {
                            startActivity(it)
                        }
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create().show()
            }

            binding.btnSubmission.setOnClickListener {
                val dialog = SubmitDialog(this)
                dialog.show()
            }

            binding.btnBet.setOnClickListener {
                BetActivity.getIntent(this).also {
                    startActivity(it)
                }
            }
        }
    }

    override fun onBackPressed() {
        Authentication.signOut()
        toast("You have signed out!")
        super.onBackPressed()
    }
}