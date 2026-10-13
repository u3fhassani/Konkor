package com.project.konkor.ui.fragment.options

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.project.konkor.R
import com.project.konkor.databinding.FragmentAboutUsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUsFragment : Fragment(R.layout.fragment_about_us) {

    private lateinit var binding: FragmentAboutUsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutUsBinding.bind(view)

        binding.appBarLayoutAboutUs.isVisible = arguments?.getInt("auth") == 1

        binding.imageViewAboutUsBackPressed.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

}