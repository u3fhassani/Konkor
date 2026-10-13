package com.project.konkor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.konkor.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject
constructor(
    questionRepository: QuestionRepository
) : ViewModel() {

    val favoriteListCat = questionRepository.getAllFavorite().asLiveData()

}