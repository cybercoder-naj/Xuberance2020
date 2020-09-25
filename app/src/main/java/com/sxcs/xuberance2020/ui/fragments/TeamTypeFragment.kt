package com.sxcs.xuberance2020.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.databinding.FragmentTeamTypeBinding

class TeamTypeFragment : Fragment(R.layout.fragment_team_type) {

    private lateinit var binding: FragmentTeamTypeBinding

    companion object {
        private const val PARAM1 = "param1"

        fun newInstance(param1: Int) = TeamTypeFragment().apply {
            arguments = Bundle().apply {
                putInt(PARAM1, param1)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTeamTypeBinding.bind(view)
    }
}