package com.sxcs.xuberance2020.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sxcs.xuberance2020.data.models.EventDetails
import com.sxcs.xuberance2020.ui.fragments.ConfirmationFragment
import com.sxcs.xuberance2020.ui.fragments.RegisterEventFragment

class RegistrationPagerAdapter(
    fragmentManager: FragmentManager,
    private val list: MutableList<EventDetails>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = list.size + 1

    override fun getItem(position: Int) =
        if (position != count - 1)
            RegisterEventFragment.newInstance(list[position])
        else ConfirmationFragment.newInstance()
}