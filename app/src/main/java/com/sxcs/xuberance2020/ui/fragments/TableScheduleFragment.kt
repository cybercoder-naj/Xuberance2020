package com.sxcs.xuberance2020.ui.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.*
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.sxcs.xuberance2020.R
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
            binding.textViewType.text = "$it Events"
            loadDataIntoRecyclerView(it)
        }
    }

    private fun loadDataIntoRecyclerView(type: EventType) {
        Database.getEventsFromEventType(type) { events ->
            events?.let { e ->
                val day1 = e.filter { it.day == 1 }
                val day2 = e.filter { it.day != 1 }
                // TODO add sorting
                populateLinearLayout(day1, day2, type)
            }
        }
    }

    private fun populateLinearLayout(day1: List<EventDetails>, day2: List<EventDetails>, type: EventType) {
        with(binding) {
            layoutScheduleDay1.removeAllViews()
            layoutScheduleDay1.weightSum = day1.size.toFloat() + 1f
            TextView(requireContext()).apply {
                text = "Day 1"
                setTextColor(Color.WHITE)
                setShadowLayer(8f, -2f, -2f, Color.BLACK)
                setTextAppearance(android.R.style.TextAppearance_Large)
                setPadding(8, 8, 8, 8)
                gravity = Gravity.CENTER
                layoutParams = LayoutParams(MATCH_PARENT, 0, 0.8f)
                typeface = ResourcesCompat.getFont(requireContext(), R.font.atmospheric)
            }.also {
                layoutScheduleDay1.addView(it)
            }
            for (i in day1.indices) {
                val event = day1[i]
                val rowLayout = LinearLayout(requireContext()).apply {
                    orientation = HORIZONTAL
                    layoutParams = LayoutParams(MATCH_PARENT, 0, 1.2f)
                    weightSum = 2f
                    showDividers = SHOW_DIVIDER_MIDDLE
                    dividerDrawable = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
                }
                TextView(requireContext()).apply {
                    //language=html
                    text = HtmlCompat.fromHtml(
                        "<b>${event.name}</b><br>${event.meaning}",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    setTextColor(Color.WHITE)
                    textSize = 16f
                    setPadding(16, 16, 16, 16)
                    layoutParams = LayoutParams(0, 132, 1f)
                    typeface = Typeface.create(typeface, Typeface.NORMAL)
                }.also {
                    rowLayout.addView(it)
                }
                TextView(requireContext()).apply {
                    text = if (type == EventType.ENTRY) event.date else event.time
                    setTextColor(Color.WHITE)
                    textSize = 16f
                    setPadding(16, 16, 16, 16)
                    gravity = Gravity.CENTER
                    layoutParams = LayoutParams(0, 132, 1f)
                    typeface = Typeface.create(typeface, Typeface.NORMAL)
                }.also {
                    rowLayout.addView(it)
                }
                if (rowLayout.parent != null)
                    (rowLayout.parent as ViewGroup).removeView(rowLayout)
                layoutScheduleDay1.addView(rowLayout)
            }
            layoutScheduleDay2.removeAllViews()
            layoutScheduleDay2.weightSum = day2.size.toFloat() + 1f
            TextView(requireContext()).apply {
                text = "Day 2"
                setTextColor(Color.WHITE)
                setShadowLayer(8f, -2f, -2f, Color.BLACK)
                setTextAppearance(android.R.style.TextAppearance_Large)
                gravity = Gravity.CENTER
                layoutParams = LayoutParams(MATCH_PARENT, 0, 0.8f)
                typeface = ResourcesCompat.getFont(requireContext(), R.font.atmospheric)
            }.also {
                layoutScheduleDay2.addView(it)
            }
            for (i in day2.indices) {
                val event = day2[i]
                val rowLayout = LinearLayout(requireContext()).apply {
                    orientation = HORIZONTAL
                    layoutParams = LayoutParams(MATCH_PARENT, 0, 1.2f)
                    weightSum = 2f
                    if (i == day2.size - 1)
                        setPadding(0, 0, 0, 100)
                    showDividers = SHOW_DIVIDER_MIDDLE
                    dividerDrawable = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
                }
                TextView(requireContext()).apply {
                    //language=html
                    text = HtmlCompat.fromHtml(
                        "<b>${event.name}</b><br>${event.meaning}",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    setTextColor(Color.WHITE)
                    textSize = 16f
                    setPadding(16, 16, 16, 16)
                    gravity = Gravity.CENTER
                    layoutParams = LayoutParams(0, WRAP_CONTENT, 1f)
                    typeface = Typeface.create(typeface, Typeface.NORMAL)
                }.also {
                    rowLayout.addView(it)
                }
                TextView(requireContext()).apply {
                    text = if (type == EventType.ENTRY) event.date else event.time
                    setTextColor(Color.WHITE)
                    textSize = 16f
                    setPadding(16, 16, 16, 16)
                    layoutParams = LayoutParams(0, WRAP_CONTENT, 1f)
                    typeface = Typeface.create(typeface, Typeface.NORMAL)
                }.also {
                    rowLayout.addView(it)
                }
                if (rowLayout.parent != null)
                    (rowLayout.parent as ViewGroup).removeView(rowLayout)
                layoutScheduleDay2.addView(rowLayout)
            }
        }
    }
}