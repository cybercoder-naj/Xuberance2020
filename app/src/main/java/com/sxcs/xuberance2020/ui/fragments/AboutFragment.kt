package com.sxcs.xuberance2020.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {

    private lateinit var binding: FragmentAboutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAboutBinding.bind(view)
    }
}