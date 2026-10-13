package com.project.konkor.viewmodel

import androidx.lifecycle.*
import com.project.konkor.repository.AuthRepository
import com.project.konkor.repository.SettingRepository
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.sharedPref.SharedPreferencesSetting
import com.project.konkor.util.SetFonts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SettingViewModel
@Inject
constructor(
    private val settingRepository: SettingRepository,
    private val sharedPrefAuth : SharedPreferencesAuth,
    private val setFonts: SetFonts
) : ViewModel() {


    fun themeMode(): Int {
        return settingRepository.themeMode()
    }
    fun fontMode(): Int {
        return settingRepository.fontMode()
    }
    fun isScreenOn(): Boolean {
        return settingRepository.isScreenOn()
    }

    fun setFonts(): SetFonts {
        return setFonts
    }

    fun signOut() {
        sharedPrefAuth.signOut()
    }

    fun email() : String {
        return sharedPrefAuth.email
    }

    fun saveThemeMode(theme: Int) {
        viewModelScope.launch {
            settingRepository.saveThemeMode(theme)
        }
    }

    fun saveFontMode(font: Int) {
        viewModelScope.launch {
            settingRepository.saveFontMode(font)
        }
    }

    fun saveScreenOn(isScreenOn: Boolean) {
        viewModelScope.launch {
            settingRepository.saveScreenOn(isScreenOn)
        }
    }


}
