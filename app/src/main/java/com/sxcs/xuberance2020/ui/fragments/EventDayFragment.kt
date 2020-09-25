package com.sxcs.xuberance2020.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.VERTICAL
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.adapters.EventScheduleRecyclerAdapter
import com.sxcs.xuberance2020.data.EventType
import com.sxcs.xuberance2020.databinding.FragmentEventDayBinding
import com.sxcs.xuberance2020.firebase.Database
import com.sxcs.xuberance2020.utils.toast

class EventDayFragment : Fragment(R.layout.fragment_event_day) {
    private lateinit var binding: FragmentEventDayBinding

    companion object {
        private const val PARAM_1 = "param1"

        fun newInstance(eventType: EventType) = EventDayFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PARAM_1, eventType)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEventDayBinding.bind(view)
        val eventType = arguments?.getSerializable(PARAM_1)!! as EventType

        setWriteupText(eventType)
        loadDataToRecyclerView(eventType)
    }

    private fun setWriteupText(eventType: EventType) {
        binding.textViewType.text = "$eventType Events"
    }

    private fun loadDataToRecyclerView(eventType: EventType) {
        Database.getEventsFromEventType(eventType) { events ->
            events?.let {
                val eventAdapter = EventScheduleRecyclerAdapter(requireActivity(), it)
                binding.recyclerViewEventsSchedule.apply {
                    layoutManager = GridLayoutManager(requireContext(), 2, VERTICAL, false)
                    adapter = eventAdapter
                    setHasFixedSize(true)
                }
            } ?: requireContext().toast("Error Encountered in collecting data.")
        }
    }
}