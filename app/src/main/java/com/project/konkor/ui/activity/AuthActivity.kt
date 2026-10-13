package com.project.konkor.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.project.konkor.R
import com.project.konkor.databinding.ActivityAuthBinding
import com.project.konkor.util.Constants
import com.project.konkor.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val settingViewModel : SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        when (settingViewModel.fontMode()) {
            Constants.FONT_BADR -> setTheme(R.style.ThemeChangeFontToBadr)
            Constants.FONT_B_NAZANIN -> setTheme(R.style.ThemeChangeFontToBNazanin)
            Constants.FONT_MITRA -> setTheme(R.style.ThemeChangeFontToMitra)
            Constants.FONT_MORVARID -> setTheme(R.style.ThemeChangeFontToMorvarid)
        }
        setContentView(binding.root)

        setupNavController()

    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerAuth) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.authFragment)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

}