package com.project.konkor.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.navigation.NavigationView
import com.project.konkor.R
import com.project.konkor.databinding.ActivityMainBinding
import com.project.konkor.interfaces.DialogEventListener
import com.project.konkor.ui.dialog.DialogSignOut
import com.project.konkor.ui.fragment.profile.ProfileFragment
import com.project.konkor.util.Constants.FONT_B_NAZANIN
import com.project.konkor.util.Constants.FONT_BADR
import com.project.konkor.util.Constants.FONT_MITRA
import com.project.konkor.util.Constants.FONT_MORVARID
import com.project.konkor.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    DialogEventListener.SignOutEvent {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val settingViewModel: SettingViewModel by viewModels()

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            // Notification permission handled
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        when (settingViewModel.fontMode()) {
            FONT_BADR -> setTheme(R.style.ThemeChangeFontToBadr)
            FONT_B_NAZANIN -> setTheme(R.style.ThemeChangeFontToBNazanin)
            FONT_MITRA -> setTheme(R.style.ThemeChangeFontToMitra)
            FONT_MORVARID -> setTheme(R.style.ThemeChangeFontToMorvarid)
        }
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavigationMain.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.yearKonkorFragment,
                R.id.profileFragment,
                R.id.resultFragment
            ),
            binding.drawerLayoutMain
        )

        setSupportActionBar(binding.toolbarMain)
        binding.toolbarMain.setupWithNavController(navController, appBarConfiguration)

        binding.navigationViewMain.setNavigationItemSelectedListener(this)

        setupDestinationChangedListener()

        keepScreenOn(settingViewModel.isScreenOn())
        setupOnBackPressed()
        checkNotificationPermission()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    fun keepScreenOn(isScreenOn: Boolean) {
        findViewById<ConstraintLayout>(R.id.constraintLayoutRootMain)
            .keepScreenOn = isScreenOn
    }


    private fun setupDestinationChangedListener() {

        settingViewModel.setFonts().setFontOnView(binding.drawerLayoutMain)
        settingViewModel.setFonts().setFontOnView(binding.toolbarMain)
        settingViewModel.setFonts().setFontOnView(binding.drawerLayoutMain)

        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->


            if (nd.id == R.id.profileFragment) {
                binding.toolbarMain.menu.setGroupVisible(R.id.profile_menu, true)
            } else {
                binding.toolbarMain.menu.setGroupVisible(R.id.profile_menu, false)
            }

            if (nd.id == R.id.yearKonkorFragment ||
                nd.id == R.id.profileFragment ||
                nd.id == R.id.resultFragment
            ) {

//                if (!bottomNavigation.isVisible) {
//                    bottomNavigation.apply {
//                        translationY = 100f
//                        animate().translationY(0f).setDuration(500).setStartDelay(100).start()
//                    }
//                }


                binding.bottomNavigationMain.visibility = VISIBLE

                unlockDrawer()

            } else {

                binding.bottomNavigationMain.visibility = GONE

                lockDrawer()
            }
        }
    }

    fun setupTextToolbar(title: String) {
        binding.toolbarMain.title = title
    }

    private fun lockDrawer() {
        binding.drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun unlockDrawer() {
        binding.drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_toolbar_menu, menu)
        return true
    }


    private fun setupOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayoutMain.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val activeFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
            ?: navHostFragment.childFragmentManager.fragments.firstOrNull()

        return when (item.itemId) {
            R.id.edit_profile -> {
                if (activeFragment is ProfileFragment) {
                    activeFragment.editProfile()
                }
                true
            }
            R.id.edit_password -> {
                if (activeFragment is ProfileFragment) {
                    activeFragment.editPassword()
                }
                true
            }
            R.id.premium_account -> {
                Toast.makeText(this, "premium account", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.signOut -> {

                if (settingViewModel.email().isNotEmpty()) {
                    val dialog = DialogSignOut()
                    dialog.show(navHostFragment.childFragmentManager, null)
                } else {
                    Toast.makeText(
                        this,
                        "شما هنوز وارد حساب کاربری خود نشده اید",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
            else -> {
                item.onNavDestinationSelected(navController)
            }
        }
    }

    override fun onClickSignOut(dialog: MaterialDialog) {
        settingViewModel.signOut()
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
        dialog.dismiss()
    }


}