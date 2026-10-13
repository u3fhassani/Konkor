package com.project.konkor.viewmodel

import androidx.lifecycle.*
import com.project.konkor.model.LessonModel
import com.project.konkor.model.QuestionModel
import com.project.konkor.model.ResultModel
import com.project.konkor.repository.QuestionRepository
import com.project.konkor.repository.SettingRepository
import com.project.konkor.util.DataState
import com.project.konkor.util.SetFonts
import dagger.hilt.android.lifecycle.HiltViewModel
import com.project.konkor.util.PersianCalendar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class QuestionViewModel
@Inject
constructor(
    private val questionRepository: QuestionRepository,
    private val settingRepository: SettingRepository,
    val setFonts: SetFonts,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val lessonModel: LessonModel? = savedStateHandle.get<LessonModel>("lessonModel")
    val questionOrAnswer: Int? = savedStateHandle.get<Int>("data")

    private val _questionFromNetwork = MutableLiveData<DataState<List<QuestionModel>>>()
    val questionFromNetwork: LiveData<DataState<List<QuestionModel>>>
        get() = _questionFromNetwork

    private val _favoriteExist = MutableLiveData<Boolean>()
    val favoriteExist: LiveData<Boolean>
        get() = _favoriteExist

    init {
        getQuestionFromNetwork()
    }

    fun getQuestionFromNetwork() {
        val model = lessonModel ?: return
        val idYear = model.idYear ?: return
        val category = model.category ?: return

        questionRepository.getQuestionFromNetwork(
            idYear,
            category,
            model.lesson,
            model.isNewSystem ?: false,
            model.isAbroad ?: false
        )
            .onEach { _questionFromNetwork.value = it }
            .launchIn(viewModelScope)
    }

    fun insertResult(responseRate: String, trueAnswer: Int, falseAnswer: Int, notAnswer: Int) {
        val model = lessonModel ?: return
        val category = model.category ?: return
        val idYear = model.idYear?.toIntOrNull() ?: 0

        viewModelScope.launch {
            val resultModel = ResultModel(
                randomId(),
                category,
                idYear,
                model.lessonPersianName,
                model.questionCount,
                responseRate,
                trueAnswer,
                falseAnswer,
                notAnswer,
                getCurrentDate(),
                model.isNewSystem ?: false,
                model.isAbroad ?: false
            )

            questionRepository.insertResult(resultModel)
        }
    }

    fun favoriteState(favoriteStateEvent: FavoriteStateEvent) {
        val model = lessonModel ?: return

        when (favoriteStateEvent) {
            FavoriteStateEvent.ExistFavorite -> {
                val idYear = model.idYear ?: return
                val category = model.category ?: return

                questionRepository.getFavoriteById(
                    idYear,
                    category,
                    model.lesson,
                    model.isNewSystem ?: false,
                    model.isAbroad ?: false
                )
                    .onEach { _favoriteExist.value = it }
                    .launchIn(viewModelScope)
            }
            FavoriteStateEvent.InsertFavorite -> {
                viewModelScope.launch {
                    questionRepository.insertFavorite(model)
                    _favoriteExist.value = true
                }
            }
            FavoriteStateEvent.DeleteFavorite -> {
                viewModelScope.launch {
                    questionRepository.deleteFavorite(model)
                    _favoriteExist.value = false
                }
            }
        }
    }

    private fun randomId(): String {
        return UUID.randomUUID().toString()
    }

    private fun getCurrentDate(): String {
        val p = PersianCalendar()
        return "${p.persianShortDateTime} ${p.persianWeekDayName}"
    }

    fun fontMode(): Int {
        return settingRepository.fontMode()
    }

}

sealed class FavoriteStateEvent {
    object DeleteFavorite : FavoriteStateEvent()
    object InsertFavorite : FavoriteStateEvent()
    object ExistFavorite : FavoriteStateEvent()
}
