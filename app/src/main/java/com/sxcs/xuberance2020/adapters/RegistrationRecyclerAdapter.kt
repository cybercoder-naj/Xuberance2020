package com.sxcs.xuberance2020.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sxcs.xuberance2020.ui.views.ParticipantEntry

class RegistrationRecyclerAdapter(
    private val number: Int
) : RecyclerView.Adapter<RegistrationRecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val mList = mutableListOf<ParticipantEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ParticipantEntry(parent.context)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as ParticipantEntry).apply {
            participantId = position + 1
        }.also {
            mList.add(it)
        }
    }

    override fun getItemCount() = number

    fun getItem(position: Int) = mList[position]
}