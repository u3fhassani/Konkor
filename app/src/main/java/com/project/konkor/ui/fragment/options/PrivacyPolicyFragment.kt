package com.project.konkor.ui.fragment.options

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.project.konkor.R
import com.project.konkor.databinding.FragmentPrivacyPolicyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyPolicyFragment : Fragment(R.layout.fragment_privacy_policy) {

    private lateinit var binding : FragmentPrivacyPolicyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrivacyPolicyBinding.bind(view)

        binding.appBarLayoutPrivacyPolicy.isVisible = arguments?.getInt("auth") == 1


        binding.imageViewPrivacyPolicyBackPressed.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }


}