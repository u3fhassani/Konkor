package com.project.konkor.viewmodel

import androidx.lifecycle.*
import com.project.konkor.model.LessonModel
import com.project.konkor.model.MethodModel
import com.project.konkor.repository.MethodRepository
import com.project.konkor.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MethodViewModel
@Inject
constructor(
    private val repository: MethodRepository
) : ViewModel() {

    private var _getMethodFromServer = MutableLiveData<DataState<List<MethodModel>>>()
    private var _getMethodFromDB = MutableLiveData<List<MethodModel>>()

    val getMethodFromServer: LiveData<DataState<List<MethodModel>>>
        get() = _getMethodFromServer

    val getMethodFromDB = repository.getMethodFromDB().asLiveData()

    init {
        getMethodFromServer()
        getMethodFromDB()
    }

    fun getMethodFromServer() {
        viewModelScope.launch {
            repository.getMethodFromServer()
                .onEach {
                    _getMethodFromServer.value = it
                }
                .launchIn(viewModelScope)
        }
    }

    fun getMethodFromDB() {
        viewModelScope.launch {
            repository.getMethodFromDB()
                .onEach {
                    _getMethodFromDB.value = it
                }
                .launchIn(viewModelScope)
        }
    }


}