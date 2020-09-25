package com.sxcs.xuberance2020.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.models.EventDetails
import com.sxcs.xuberance2020.data.models.Participant
import com.sxcs.xuberance2020.data.models.Registration
import com.sxcs.xuberance2020.databinding.FragmentRegisterEventBinding
import com.sxcs.xuberance2020.ui.views.ParticipantEntry

class RegisterEventFragment : Fragment(R.layout.fragment_register_event) {

    private lateinit var binding: FragmentRegisterEventBinding
    private var eventDetails: EventDetails? = null
    private var listener: OnButtonClickListener? = null

    companion object {
        private const val PARAM1 = "param1"

        fun newInstance(param1: EventDetails) = RegisterEventFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PARAM1, param1)
            }
        }
    }

    interface OnButtonClickListener {
        fun nextPage(key: String, value: Registration)
        fun backPage()
        fun finishPage()
        fun getCurrentPosition(): Int
        fun getChildCount(): Int
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnButtonClickListener) {
            listener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRegisterEventBinding.bind(view)

        eventDetails = arguments?.getSerializable(PARAM1) as EventDetails?
        eventDetails?.let { event ->
            binding.eventName.text = event.name
            if (tag != "android:switcher:${R.id.view_pager_registration}:0")
                binding.btnBack.isVisible = true
            if (tag == "android:switcher:${R.id.view_pager_registration}:${
                    listener?.getChildCount()?.minus(2)
                }"
            )
                binding.btnNext.text = getString(R.string.finish)

            for (i in 1..event.numberPart) {
                binding.layoutEntries.addView(ParticipantEntry(requireContext()).apply {
                    participantId = i
                })
            }
            binding.btnNext.setOnClickListener {
                if (binding.btnNext.text == getString(R.string.next)) {
                    val registrations = getRegistration()
                    if (registrations.participants.all { it != null })
                        listener?.nextPage(event.name, registrations)
                } else if (binding.btnNext.text == getString(R.string.finish))
                    listener?.finishPage()
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

    private fun getRegistration(): Registration {
        val participants = mutableListOf<Participant?>()
        for (i in 1..binding.layoutEntries.childCount) {
            val participant =
                (binding.layoutEntries.getChildAt(i - 1) as ParticipantEntry).participant
            participants.add(participant)
        }
        return Registration(participants)
    }
}