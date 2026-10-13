package com.project.konkor.sharedPref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.project.konkor.util.Constants.FONT_MODE
import com.project.konkor.util.Constants.SCREEN_ON
import com.project.konkor.util.Constants.THEME_MODE


class SharedPreferencesSetting(context: Context) {

    private val sharedPrefSetting: SharedPreferences =
        context.getSharedPreferences("setting", MODE_PRIVATE)

    fun changeFontSetting(changeListener: SharedPreferences.OnSharedPreferenceChangeListener){
        sharedPrefSetting.registerOnSharedPreferenceChangeListener(changeListener)
    }

    fun saveThemeMode(theme: Int) {
        sharedPrefSetting.edit {
            putInt(THEME_MODE, theme)
        }
    }

    fun saveFontMode(font: Int) {
        sharedPrefSetting.edit {
            putInt(FONT_MODE, font)
        }
    }

    fun saveScreenOn(isScreenOn: Boolean) {
        sharedPrefSetting.edit {
            putBoolean(SCREEN_ON, isScreenOn)
        }
    }

    val themeMode: Int
        get() = sharedPrefSetting.getInt(THEME_MODE, 1)

    val fontMode: Int
        get() = sharedPrefSetting.getInt(FONT_MODE, 1)

    val isScreenOn: Boolean
        get() = sharedPrefSetting.getBoolean(SCREEN_ON, false)

    fun signOut() {
        sharedPrefSetting.edit {
            clear()
        }
    }

}