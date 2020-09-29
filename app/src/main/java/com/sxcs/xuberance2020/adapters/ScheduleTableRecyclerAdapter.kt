package com.sxcs.xuberance2020.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.models.EventDetails
import com.sxcs.xuberance2020.databinding.LayoutDayHeaderBinding
import com.sxcs.xuberance2020.databinding.LayoutTableRowBinding

private const val HEADER = 1
private const val NORMAL = 2

class ScheduleTableRecyclerAdapter(
    private val mList: List<EventDetails>,
    private val day: Int
) : RecyclerView.Adapter<ScheduleTableRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == NORMAL)
            ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_table_row,
                    parent,
                    false
                )
            )
        else
            ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_day_header,
                    parent,
                    false
                )
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == NORMAL) {
            val binding = LayoutTableRowBinding.bind(holder.itemView)
            val eventDetails = mList[position - 1]
            binding.textViewName.text = eventDetails.name
            binding.textViewMeaning.text = eventDetails.meaning
            binding.textViewTime.text = eventDetails.time
        } else if (getItemViewType(position) == HEADER) {
            val binding = LayoutDayHeaderBinding.bind(holder.itemView)
            binding.textViewDay.text = String.format("Day %d", day)
        }
    }

    override fun getItemCount() = mList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER else NORMAL
    }
}