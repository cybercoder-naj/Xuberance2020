package com.sxcs.xuberance2020.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.models.Registration
import com.sxcs.xuberance2020.databinding.FragmentConfirmationBinding
import com.sxcs.xuberance2020.firebase.Database
import com.sxcs.xuberance2020.utils.getList

class ConfirmationFragment private constructor() : Fragment(R.layout.fragment_confirmation) {

    private lateinit var binding: FragmentConfirmationBinding
    private var listener: OnFinishRegistrationListener? = null

    interface OnFinishRegistrationListener {
        fun getRegistrationMap(): HashMap<String, Registration>
        fun backPage()
    }

    companion object {
        fun newInstance() = ConfirmationFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFinishRegistrationListener)
            listener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentConfirmationBinding.bind(view)

        val registrationMap = listener?.getRegistrationMap()
        registrationMap?.let {
            binding.textViewList.text =
                HtmlCompat.fromHtml(it.getList(), HtmlCompat.FROM_HTML_MODE_LEGACY)

            binding.confirmRegistrations.setOnClickListener { v ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Submitting Registrations...")
                    .setMessage("Are you sure you want to Proceed? You cannot undo this operation.")
                    .setPositiveButton("Yes") { dialog, _ ->
                        Database.submitRegistrations(it)
                        requireActivity().finish()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Go back to review") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create().show()
            }

            binding.btnBack.setOnClickListener {
                listener?.backPage()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}