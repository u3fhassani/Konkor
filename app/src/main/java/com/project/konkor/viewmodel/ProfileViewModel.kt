package com.project.konkor.viewmodel

import androidx.lifecycle.*
import com.project.konkor.model.AuthModel
import com.project.konkor.model.TargetModel
import com.project.konkor.repository.ProfileRepository
import com.project.konkor.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val repository: ProfileRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _targetList: MutableLiveData<List<TargetModel>> = MutableLiveData()
    private val _editProfile: MutableLiveData<DataState<AuthModel>> = MutableLiveData()
    private val _editPassword: MutableLiveData<DataState<Boolean>> = MutableLiveData()

    val targetList: LiveData<List<TargetModel>>
        get() = _targetList

    val editProfile: LiveData<DataState<AuthModel>>
        get() = _editProfile

    val editPassword: LiveData<DataState<Boolean>>
        get() = _editPassword

    fun getTargetList() {
        viewModelScope.launch {
            repository.getAllTarget()
                .onEach {
                    _targetList.value = it
                }
                .launchIn(viewModelScope)
        }
    }

    fun editProfile(
        firstName: String,
        lastName: String,
        age: Int,
        category: String
    ) {
        viewModelScope.launch {
            repository.editProfile(firstName, lastName, age, category)
                .onEach {
                    _editProfile.value = it
                }
                .launchIn(viewModelScope)
        }
    }

    fun editPassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            repository.editPassword(oldPassword, newPassword)
                .onEach {
                    _editPassword.value = it
                }
                .launchIn(viewModelScope)
        }
    }

    fun update(targetModel: TargetModel) {
        viewModelScope.launch {
            repository.updateTarget(targetModel)
        }
    }

    fun insert(targetModel: TargetModel) {
        viewModelScope.launch {
            repository.insertTarget(targetModel)
        }
    }

    fun delete(targetModel: TargetModel) {
        viewModelScope.launch {
            repository.deleteTarget(targetModel)
        }
    }

}