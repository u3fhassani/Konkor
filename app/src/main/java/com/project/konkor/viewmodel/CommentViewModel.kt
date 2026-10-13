package com.project.konkor.viewmodel

import androidx.lifecycle.*
import com.project.konkor.repository.CommentRepository
import com.project.konkor.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel
@Inject
constructor(
    private val commentRepository: CommentRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _responseComment: MutableLiveData<DataState<Boolean>> = MutableLiveData()

    val responseComment: LiveData<DataState<Boolean>>
        get() = _responseComment


    fun responseComment(comment: String) {
        viewModelScope.launch {
            commentRepository.sendComment(comment)
                .onEach {
                    _responseComment.value = it
                }
                .launchIn(viewModelScope)
        }
    }


}