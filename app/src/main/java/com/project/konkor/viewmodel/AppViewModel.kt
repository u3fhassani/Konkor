package com.project.konkor.viewmodel

import androidx.lifecycle.ViewModel
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.sharedPref.SharedPreferencesSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel
@Inject
constructor(
    private val sharedPrefAuth : SharedPreferencesAuth,
    private val sharedPrefSetting : SharedPreferencesSetting
) : ViewModel() {





}