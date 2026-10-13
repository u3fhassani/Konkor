package com.project.konkor.viewmodel

import androidx.lifecycle.*
import com.project.konkor.model.AuthModel
import com.project.konkor.repository.AuthRepository
import com.project.konkor.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val repository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _signIn = MutableLiveData<DataState<AuthModel>>()
    private val _register = MutableLiveData<DataState<AuthModel>>()
    private val _category = MutableLiveData<DataState<Boolean>>()
    private val _forgetPassword = MutableLiveData<DataState<Boolean>>()

    val signIn: LiveData<DataState<AuthModel>>
        get() = _signIn
    val register: LiveData<DataState<AuthModel>>
        get() = _register
    val category: LiveData<DataState<Boolean>>
        get() = _category
    val forgetPassword: LiveData<DataState<Boolean>>
        get() = _forgetPassword

    fun signInMethod(
        email: String,
        password: String,
        signInStateEvent: SignInStateEvent
    ) {
        when (signInStateEvent) {
            is SignInStateEvent.SignInEvents -> {
                viewModelScope.launch {
                    repository.signIn(email, password)
                        .onEach { dataState ->
                            _signIn.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
            is SignInStateEvent.None -> {
                // fuck you
            }

        }


    }


    fun registerMethod(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {

        viewModelScope.launch {
            repository.register(firstName, lastName, email, password)
                .onEach { dataState ->
                    _register.value = dataState
                }
                .launchIn(viewModelScope)
        }

    }

    fun categoryMethod(category: String) {
        viewModelScope.launch {
            repository.saveCategory(category)
                .onEach {
                    _category.value = it
                }
                .launchIn(viewModelScope)
        }
    }

    fun forgetPassword(email: String) {
        viewModelScope.launch {
            repository.forgetPassword(email)
                .onEach {
                    _forgetPassword.value = it
                }
                .launchIn(viewModelScope)
        }
    }


}

sealed class SignInStateEvent {

    object SignInEvents : SignInStateEvent()

    object None : SignInStateEvent()

}
