package com.sxcs.xuberance2020.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sxcs.xuberance2020.data.EventType
import com.sxcs.xuberance2020.ui.fragments.TableFragment

class SchedulePagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount() = 3

    override fun getItem(position: Int) =
        TableFragment.newInstance(EventType.values()[position + 1])
}