package com.sxcs.xuberance2020.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.VERTICAL
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.adapters.SponsorsRecyclerAdapter
import com.sxcs.xuberance2020.data.Constants.mSponsors
import com.sxcs.xuberance2020.databinding.FragmentSponsorsBinding

class SponsorsFragment : Fragment(R.layout.fragment_sponsors) {

    private lateinit var binding: FragmentSponsorsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSponsorsBinding.bind(view)

        binding.recyclerViewSponsor.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, VERTICAL, false)
            adapter = SponsorsRecyclerAdapter(mSponsors)
            setHasFixedSize(true)
        }
    }
}