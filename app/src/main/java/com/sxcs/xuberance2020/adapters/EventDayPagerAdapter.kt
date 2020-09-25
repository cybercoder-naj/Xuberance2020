package com.sxcs.xuberance2020.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sxcs.xuberance2020.data.EventType
import com.sxcs.xuberance2020.ui.fragments.EventDayFragment

class EventDayPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = 4

    override fun getItem(position: Int) =
        EventDayFragment.newInstance(EventType.values()[position])
}