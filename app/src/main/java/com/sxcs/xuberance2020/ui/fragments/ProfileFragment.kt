package com.sxcs.xuberance2020.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.databinding.FragmentProfileBinding
import com.sxcs.xuberance2020.firebase.Authentication
import com.sxcs.xuberance2020.firebase.Database
import com.sxcs.xuberance2020.ui.activities.BetActivity
import com.sxcs.xuberance2020.ui.activities.RegistrationActivity
import com.sxcs.xuberance2020.ui.dialogs.SubmitDialog
import com.sxcs.xuberance2020.utils.toast

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        if (Authentication.user == null)
            requireContext().toast("Unexpected error. Try logging in again.")
        else {
            Database.canRegistered {
                if (it)
                    binding.btnRegistration.isEnabled = false
            }

            Database.getSchoolName { name ->
                name?.let {
                    binding.textViewName.text = it
                } ?: requireContext().toast("Error getting school name.")
            }

            binding.btnRegistration.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Entering Registration Panel...")
                    .setMessage("Make sure you have all the list of the names of Participants and their valid phone numbers.")
                    .setPositiveButton("Continue") { dialog, _ ->
                        RegistrationActivity.getIntent(requireContext()).also {
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
                val dialog = SubmitDialog(requireContext())
                dialog.show()
            }

            binding.btnBet.setOnClickListener {
                BetActivity.getIntent(requireContext()).also {
                    startActivity(it)
                }
            }
        }
    }
}