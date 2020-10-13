package com.sxcs.xuberance2020.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.PagerSnapHelper
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.adapters.TeamRecyclerAdapter
import com.sxcs.xuberance2020.adapters.TextRecyclerAdapter
import com.sxcs.xuberance2020.data.Constants.teacherPictures
import com.sxcs.xuberance2020.data.Constants.teamPictures
import com.sxcs.xuberance2020.databinding.FragmentTeamBinding

class TeamFragment : Fragment(R.layout.fragment_team) {

    private lateinit var binding: FragmentTeamBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTeamBinding.bind(view)

        val snapHelper1 = PagerSnapHelper()
        val snapHelper2 = PagerSnapHelper()

        binding.recyclerViewCore.apply {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            isNestedScrollingEnabled = false
            adapter = TeamRecyclerAdapter(teamPictures)
            setHasFixedSize(true)
        }
        snapHelper1.attachToRecyclerView(binding.recyclerViewCore)

        binding.recyclerViewTeachers.apply {
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            isNestedScrollingEnabled = false
            adapter = TeamRecyclerAdapter(teacherPictures)
            setHasFixedSize(true)
        }
        snapHelper2.attachToRecyclerView(binding.recyclerViewTeachers)

        /*binding.recyclerViewExecutives.apply {
            layoutManager = object: LinearLayoutManager(requireContext(), VERTICAL, false) {
                override fun canScrollVertically() = false
            }
            isNestedScrollingEnabled = false
            adapter = TextRecyclerAdapter(requireContext().resources.getStringArray(R.array.names))
            setHasFixedSize(true)
        }*/
    }
}