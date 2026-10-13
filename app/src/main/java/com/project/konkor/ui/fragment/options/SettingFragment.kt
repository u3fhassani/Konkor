package com.project.konkor.ui.fragment.options

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.konkor.R
import com.project.konkor.databinding.FragmentSettingBinding
import com.project.konkor.ui.activity.MainActivity
import com.project.konkor.util.Constants.DARK_THEME
import com.project.konkor.util.Constants.DAY_THEME
import com.project.konkor.util.Constants.DEFAULT_THEME
import com.project.konkor.util.Constants.FONT_B_NAZANIN
import com.project.konkor.util.Constants.FONT_BADR
import com.project.konkor.util.Constants.FONT_MITRA
import com.project.konkor.util.Constants.FONT_MORVARID
import com.project.konkor.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var binding: FragmentSettingBinding
    private val settingViewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

        setupTheme()
        setupFont()
        setupScreenOn()

    }

    private fun setupTheme() {


        binding.rbSettingDefaultTheme.isEnabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P


        when (settingViewModel.themeMode()) {
            DARK_THEME -> {
                binding.radioGroupSettingDarkTheme.check(R.id.rb_setting_nightTheme)
            }
            DAY_THEME -> {
                binding.radioGroupSettingDarkTheme.check(R.id.rb_setting_dayTheme)
            }
            DEFAULT_THEME -> {
                binding.radioGroupSettingDarkTheme.check(R.id.rb_setting_defaultTheme)
            }
        }


        binding.radioGroupSettingDarkTheme.setOnCheckedChangeListener { _, checkedId ->


            when (checkedId) {
                R.id.rb_setting_nightTheme -> {
                    settingViewModel.saveThemeMode(DARK_THEME)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                R.id.rb_setting_dayTheme -> {
                    settingViewModel.saveThemeMode(DAY_THEME)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.rb_setting_defaultTheme -> {
                    settingViewModel.saveThemeMode(DEFAULT_THEME)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }


        }


    }

    private fun setupFont() {

        when (settingViewModel.fontMode()) {
            FONT_BADR -> {
                binding.radioGroupSettingFont.check(R.id.rb_setting_font_badr)
            }
            FONT_B_NAZANIN -> {
                binding.radioGroupSettingFont.check(R.id.rb_setting_font_nazanin)
            }
            FONT_MITRA -> {
                binding.radioGroupSettingFont.check(R.id.rb_setting_font_mitra)
            }
            FONT_MORVARID -> {
                binding.radioGroupSettingFont.check(R.id.rb_setting_font_morvarid)
            }
        }

        binding.radioGroupSettingFont.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_setting_font_badr -> {
                    settingViewModel.saveFontMode(FONT_BADR)
                }
                R.id.rb_setting_font_nazanin -> {
                    settingViewModel.saveFontMode(FONT_B_NAZANIN)
                }
                R.id.rb_setting_font_mitra -> {
                    settingViewModel.saveFontMode(FONT_MITRA)
                }
                R.id.rb_setting_font_morvarid -> {
                    settingViewModel.saveFontMode(FONT_MORVARID)
                }
            }

            (requireActivity() as MainActivity).recreate()

        }




    }

    private fun setupScreenOn() {

        binding.switchSettingScreenOn.isChecked = settingViewModel.isScreenOn()

        binding.switchSettingScreenOn.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveScreenOn(isChecked)
            (activity as MainActivity).keepScreenOn(isChecked)
        }

    }

}