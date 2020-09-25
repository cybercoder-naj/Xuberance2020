package com.sxcs.xuberance2020.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.models.EventDetails
import com.sxcs.xuberance2020.databinding.LayoutEventItemBinding
import com.sxcs.xuberance2020.ui.activities.EventDetailsActivity

class EventScheduleRecyclerAdapter(
    private val activity: Activity,
    private val mList: MutableList<EventDetails>
) : RecyclerView.Adapter<EventScheduleRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(activity.applicationContext).inflate(
                R.layout.layout_event_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutEventItemBinding.bind(holder.itemView)
        with(binding) {
            if (mList[position].imageUrl != "")
                Picasso
                    .with(activity)
                    .load(mList[position].imageUrl)
                    .into(eventBackgroundImage)

            relativeParent.setOnClickListener {
                val imageViewPair = Pair.create(
                    eventBackgroundImage as View,
                    activity.getString(R.string.transition_time)
                )
                val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageViewPair)

                EventDetailsActivity.getIntent(activity, mList[position]).also {
                    activity.startActivity(it, options.toBundle())
                }
            }
        }
    }

    override fun getItemCount() = mList.size
}