package com.project.konkor

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.TokenWatcher
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationManagerCompat
import com.project.konkor.model.TokenContainer
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.sharedPref.SharedPreferencesSetting
import com.project.konkor.util.Constants
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlin.random.Random

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var sharedPrefSetting: SharedPreferencesSetting

    @Inject
    lateinit var sharedPrefAuth: SharedPreferencesAuth

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            Random.nextInt()
            val notificationChannel = NotificationChannel(
                getString(R.string.default_notification_channel_id),
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = ""
            notificationChannel.importance = NotificationManager.IMPORTANCE_HIGH
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)


        }

        when (sharedPrefSetting.themeMode) {
            Constants.DARK_THEME -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            Constants.DAY_THEME -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            Constants.DEFAULT_THEME -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
        TokenContainer.update(sharedPrefAuth.accessToken, sharedPrefAuth.refreshToken)
    }
}