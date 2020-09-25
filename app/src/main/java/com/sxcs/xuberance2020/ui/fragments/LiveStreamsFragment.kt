package com.sxcs.xuberance2020.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.databinding.FragmentLiveStreamsBinding

class LiveStreamsFragment : Fragment(R.layout.fragment_live_streams) {

    private lateinit var binding: FragmentLiveStreamsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLiveStreamsBinding.bind(view)


    }
}