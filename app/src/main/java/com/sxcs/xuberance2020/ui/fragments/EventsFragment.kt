package com.sxcs.xuberance2020.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.adapters.EventDayPagerAdapter
import com.sxcs.xuberance2020.databinding.FragmentEventsBinding
import com.sxcs.xuberance2020.ui.activities.EventTypeRulesActivity

class EventsFragment : Fragment(R.layout.fragment_events) {

    private lateinit var binding: FragmentEventsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEventsBinding.bind(view)

        setupViewPager()
        binding.textView1.setOnClickListener {
            Intent(requireContext(), EventTypeRulesActivity::class.java).apply {
                putExtra(EventTypeRulesActivity.EVENT_NAME, "None")
            }.also {
                startActivity(it)
            }
        }
    }

    private fun setupViewPager() {
        val eventDayPagerAdapter = EventDayPagerAdapter(childFragmentManager)
        binding.viewPagerScheduleDay.adapter = eventDayPagerAdapter
        binding.viewPagerScheduleDay.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNav.menu.findItem(R.id.mEntry).isChecked = true
                    1 -> binding.bottomNav.menu.findItem(R.id.mRecorded).isChecked = true
                    2 -> binding.bottomNav.menu.findItem(R.id.mLive).isChecked = true
                    3 -> binding.bottomNav.menu.findItem(R.id.mGroup).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mEntry -> binding.viewPagerScheduleDay.currentItem = 0
                R.id.mRecorded -> binding.viewPagerScheduleDay.currentItem = 1
                R.id.mLive -> binding.viewPagerScheduleDay.currentItem = 2
                R.id.mGroup -> binding.viewPagerScheduleDay.currentItem = 3
            }
            true
        }
    }
}