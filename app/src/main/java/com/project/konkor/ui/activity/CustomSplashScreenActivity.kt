package com.project.konkor.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.project.konkor.databinding.ActivityCustomSplashScreenBinding
import com.project.konkor.sharedPref.SharedPreferencesAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class CustomSplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefAuth: SharedPreferencesAuth
    private lateinit var binding : ActivityCustomSplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomSplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

                lifecycleScope.launch {
                    delay(700)
                    if (sharedPrefAuth.email.isEmpty()) {
                        startActivity(
                            Intent(
                                this@CustomSplashScreenActivity,
                                AuthActivity::class.java
                            )
                        )
                    } else {
                        startActivity(
                            Intent(
                                this@CustomSplashScreenActivity,
                                MainActivity::class.java
                            )
                        )
                    }
                    finish()
                }


    }


}