package com.sxcs.xuberance2020.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.databinding.LayoutTextBinding

class TextRecyclerAdapter(
    private val mList: Array<out String>
) : RecyclerView.Adapter<TextRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_text,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LayoutTextBinding.bind(holder.itemView)
        binding.txtName.text = mList[position]
    }

    override fun getItemCount() = mList.size
}