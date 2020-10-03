package com.sxcs.xuberance2020.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.EventType
import com.sxcs.xuberance2020.data.models.EventDetails
import com.sxcs.xuberance2020.databinding.LayoutEventItemBinding
import com.sxcs.xuberance2020.ui.activities.EventDetailsActivity
import com.sxcs.xuberance2020.ui.activities.EventTypeRulesActivity
import com.sxcs.xuberance2020.utils.toast

class EventScheduleRecyclerAdapter(
    private val context: Context,
    private val mList: List<EventDetails>
) : RecyclerView.Adapter<EventScheduleRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_event_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutEventItemBinding.bind(holder.itemView)
        with(binding) {
            binding.progressBar.isVisible = true
            if (mList[position].imageUrl != "")
                Picasso
                    .with(context)
                    .load(mList[position].imageUrl)
                    .into(eventBackgroundImage, object : Callback {
                        override fun onSuccess() {
                            progressBar.isVisible = false
                        }

                        override fun onError() {
                            context.toast("Error loading Image")
                            eventBackgroundImage.contentDescription = mList[position].name
                            progressBar.isVisible = false
                        }
                    })

            relativeParent.setOnClickListener {
                if (mList[position].type != EventType.GROUP.toString()) {
                    EventDetailsActivity.getIntent(context, mList[position]).also {
                        context.startActivity(it)
                    }
                } else {
                    Intent(context, EventTypeRulesActivity::class.java).apply {
                        putExtra(EventTypeRulesActivity.EVENT_NAME, mList[position].name)
                    }.also {
                        context.startActivity(it)
                    }
                }
            }
        }
    }

    override fun getItemCount() = mList.size
}