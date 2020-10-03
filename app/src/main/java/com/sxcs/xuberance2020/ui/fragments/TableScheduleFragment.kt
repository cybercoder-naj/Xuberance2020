package com.sxcs.xuberance2020.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.adapters.ScheduleTableRecyclerAdapter
import com.sxcs.xuberance2020.data.EventType
import com.sxcs.xuberance2020.data.models.EventDetails
import com.sxcs.xuberance2020.databinding.FragmentTableBinding
import com.sxcs.xuberance2020.firebase.Database

class TableFragment : Fragment(R.layout.fragment_table) {
    private lateinit var binding: FragmentTableBinding

    companion object {
        const val PARAM_1 = "param1"

        fun newInstance(eventType: EventType) = TableFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PARAM_1, eventType)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTableBinding.bind(view)
        val eventType = arguments?.getSerializable(PARAM_1) as? EventType

        eventType?.let {
            binding.textViewType.text = String.format("%s Events", it.toString())
            loadDataIntoRecyclerView(it)
        }
    }

    private fun loadDataIntoRecyclerView(type: EventType) {
        if (type != EventType.GROUP)
            Database.getEventsFromEventType(type) { events ->
                events?.let { e ->
                    e.sortBy { details -> details.slno }
                    val day1 = e.filter { it.day == 1 }
                    val day2 = e.filter { it.day != 1 }
                    binding.recyclerView1.apply {
                        adapter = ScheduleTableRecyclerAdapter(day1, 1)
                        layoutManager = object: LinearLayoutManager(requireContext(), VERTICAL, false) {
                            override fun canScrollVertically() = false
                        }
                        setHasFixedSize(true)
                    }
                    binding.recyclerView2.apply {
                        adapter = ScheduleTableRecyclerAdapter(day2, 2)
                        layoutManager = object: LinearLayoutManager(requireContext(), VERTICAL, false) {
                            override fun canScrollVertically() = false
                        }
                        setHasFixedSize(true)
                    }
                }
            }
        else {
            Database.getAllGroupEvents { list ->
                list.sortBy { it.slno }
                val day1 = list.filter { it.day == 1 }
                val day2 = list.filter { it.day != 1 }
                binding.recyclerView1.apply {
                    adapter = ScheduleTableRecyclerAdapter(day1, 1)
                    layoutManager = object: LinearLayoutManager(requireContext(), VERTICAL, false) {
                        override fun canScrollVertically() = false
                    }
                    setHasFixedSize(true)
                }
                binding.recyclerView2.apply {
                    adapter = ScheduleTableRecyclerAdapter(day2, 2)
                    layoutManager = object: LinearLayoutManager(requireContext(), VERTICAL, false) {
                        override fun canScrollVertically() = false
                    }
                    setHasFixedSize(true)
                }
            }
        }
    }
}