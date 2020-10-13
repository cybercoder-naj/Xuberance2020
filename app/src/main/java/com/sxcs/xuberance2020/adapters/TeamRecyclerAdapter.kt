package com.sxcs.xuberance2020.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.databinding.LayoutTeamImageBinding

class TeamRecyclerAdapter (
    private val mList: List<Int>
): RecyclerView.Adapter<TeamRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_team_image,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutTeamImageBinding.bind(holder.itemView)
        binding.imgPhoto.setImageResource(mList[position])
    }

    override fun getItemCount() = mList.size
}