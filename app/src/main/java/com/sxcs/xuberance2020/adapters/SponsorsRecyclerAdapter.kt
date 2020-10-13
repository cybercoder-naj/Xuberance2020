package com.sxcs.xuberance2020.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.databinding.LayoutSponsorItemBinding

class SponsorsRecyclerAdapter(
    private val mList: List<Pair<Int, String>>
) : RecyclerView.Adapter<SponsorsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_sponsor_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutSponsorItemBinding.bind(holder.itemView)
        with(binding) {
            eventBackgroundImage.setImageResource(mList[position].first)
            txtSponsorType.text = mList[position].second
        }
    }

    override fun getItemCount() = mList.size
}