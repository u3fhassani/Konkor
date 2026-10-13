package com.project.konkor.ui.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.project.konkor.R
import com.project.konkor.adapter.AuthViewPagerAdapter
import com.project.konkor.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var binding:FragmentAuthBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)



        val tabLayoutAuth = binding.tabLayoutAuth
        val viewPager = binding.viewPagerAuth

        val authAdapter = AuthViewPagerAdapter(childFragmentManager,lifecycle)
        viewPager.adapter = authAdapter

        authAdapter.addFragment(SignInFragment())
        authAdapter.addFragment(SignUpFragment())

        TabLayoutMediator(tabLayoutAuth,viewPager){ tab,position->
            if (position == 0) {
                tab.text = "ورود"
            }
            if (position == 1) {
                tab.text = "ساختن حساب"
            }
        }.attach()




        tabLayoutAuth.apply {
            translationX = 300f
            alpha = 0f
            animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(400).start()
        }



    }






}