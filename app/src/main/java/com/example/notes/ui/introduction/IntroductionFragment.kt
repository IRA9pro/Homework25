package com.example.notes.ui.introduction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.notes.data.models.IntroductionModel
import com.example.notes.R
import com.example.notes.data.local.Pref
import com.example.notes.databinding.FragmentIntroductionBinding

class IntroductionFragment : Fragment() {

    private lateinit var binding: FragmentIntroductionBinding
    private lateinit var adapter: IntroductionAdapter
    private lateinit var pref: Pref


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = Pref(requireContext())
        binding = FragmentIntroductionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = IntroductionAdapter(getList(), ::onClickStart, ::onClickStop)

        binding.vpIntro.adapter = adapter
        binding.introDotsIndicator.attachTo(binding.vpIntro)
    }

    fun getList(): List<IntroductionModel> {
        return listOf(
            IntroductionModel(
                "Convenience",
                R.string.intro_description1,
                "board_design_strategy.json"
            ),
            IntroductionModel("Organization",
                R.string.intro_description2,
                "cutting_board.json"),
            IntroductionModel(
                "Synchronization",
                R.string.intro_description3,
                "on_boarding_animation.json"
            )
        )
    }

    private fun onClickStart() {
        pref.setIntroShown()
        findNavController().navigate(R.id.action_introductionFragment_to_mainFragment)
    }

    private fun onClickStop(position: Int) {
        binding.vpIntro.currentItem = position + 1
    }
}