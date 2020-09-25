package com.sxcs.xuberance2020.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sxcs.xuberance2020.ui.fragments.TeamTypeFragment

class TeamPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = 3

    override fun getItem(position: Int) =
        TeamTypeFragment.newInstance(position)
}