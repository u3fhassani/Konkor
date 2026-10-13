package com.project.konkor.repository

import com.project.konkor.di.IoDispatcher
import com.project.konkor.sharedPref.SharedPreferencesSetting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingRepository
@Inject
constructor(
    private val sharedPrefSetting: SharedPreferencesSetting,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {


    suspend fun saveThemeMode(theme: Int) {
        withContext(ioDispatcher) {
            sharedPrefSetting.saveThemeMode(theme)
        }
    }

    suspend fun saveFontMode(font: Int) {
        withContext(ioDispatcher) {
            sharedPrefSetting.saveFontMode(font)
        }
    }

    suspend fun saveScreenOn(isScreenOn: Boolean) {
        withContext(ioDispatcher) {
            sharedPrefSetting.saveScreenOn(isScreenOn)
        }
    }

    fun themeMode(): Int {
        return sharedPrefSetting.themeMode
    }

    fun fontMode(): Int {
        return sharedPrefSetting.fontMode
    }

    fun isScreenOn(): Boolean {
        return sharedPrefSetting.isScreenOn
    }


}