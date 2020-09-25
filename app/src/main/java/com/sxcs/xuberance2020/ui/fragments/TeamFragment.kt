package com.sxcs.xuberance2020.ui.fragments

import androidx.fragment.app.Fragment
import com.sxcs.xuberance2020.R

class TeamFragment : Fragment(R.layout.layout_error_page) {

    // TODO change after the update
    /*private lateinit var binding: FragmentTeamBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTeamBinding.bind(view)

        // TODO setCheckedItem(TAB_CORE)
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPagerTeam.adapter = TeamPagerAdapter(childFragmentManager)
    }

    private fun setCheckedItem(position: Int) {
        binding.btnCore.setBackgroundResource(R.color.color_clouds)
        binding.btnExecutive.setBackgroundResource(R.color.color_clouds)
        binding.btnTeachers.setBackgroundResource(R.color.color_clouds)
        binding.viewPagerTeam.currentItem = position
        when (position) {
            0 -> binding.btnCore.setBackgroundResource(R.color.color_sky)
            1 -> binding.btnExecutive.setBackgroundResource(R.color.color_sky)
            2 -> binding.btnTeachers.setBackgroundResource(R.color.color_sky)
        }
    }*/
}