package com.project.konkor.viewmodel

import androidx.lifecycle.*
import com.project.konkor.model.ResultModel
import com.project.konkor.repository.ResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ResultViewModel
@Inject
constructor(
    private val resultRepository: ResultRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _resultByCategory = MutableLiveData<List<ResultModel>>()
    private var _resultByCategoryAndYear = MutableLiveData<List<ResultModel>>()
    private var _allResult = MutableLiveData<List<ResultModel>>()


    val resultByCategory: LiveData<List<ResultModel>>
        get() = _resultByCategory

    val resultByCategoryAndYear: LiveData<List<ResultModel>>
        get() = _resultByCategoryAndYear

    val allResult: LiveData<List<ResultModel>>
        get() = _allResult

    fun getAllResult() {
        viewModelScope.launch {
            resultRepository.getAllResult()
                .onEach {
                    _allResult.value = it
                }
                .launchIn(viewModelScope)
        }
    }

    fun resultByCategory(category: String) {
        viewModelScope.launch {
            resultRepository.getByCategory(category)
                .onEach {
                    _resultByCategory.value = it
                }
                .launchIn(viewModelScope)
        }
    }

    fun resultByCategoryAndYear(category: String, year: Int) {
        viewModelScope.launch {
            resultRepository.getByCategoryAndYear(category, year)
                .onEach {
                    _resultByCategoryAndYear.value = it
                }
                .launchIn(viewModelScope)
        }
    }


    fun insertResult(resultModel: ResultModel) {
        viewModelScope.launch {
            resultRepository.insertResult(resultModel)
        }
    }

    fun deleteResult(resultModel: ResultModel) {
        viewModelScope.launch {
            resultRepository.deleteResult(resultModel)
        }
    }

}