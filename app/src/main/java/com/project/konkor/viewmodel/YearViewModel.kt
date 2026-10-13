package com.project.konkor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.konkor.model.YearModel
import com.project.konkor.repository.YearRepository
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class YearViewModel
@Inject
constructor(
    val sharedPrefAuth: SharedPreferencesAuth,
    private val yearRepository: YearRepository
) : ViewModel() {

    private val _yearList = MutableLiveData<DataState<List<YearModel>>>()
    val yearList: LiveData<DataState<List<YearModel>>>
        get() = _yearList



    init {
        getYearData()
    }

    fun getYearData() {
        viewModelScope.launch {
            yearRepository.getYear()
                .onEach {
                    _yearList.value = it
                }
                .launchIn(viewModelScope)
        }
    }


}