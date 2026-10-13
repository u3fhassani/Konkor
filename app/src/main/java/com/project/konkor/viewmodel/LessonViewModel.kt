package com.project.konkor.viewmodel

import androidx.lifecycle.*
import com.project.konkor.model.LessonModel
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.util.Constants.ENSANI
import com.project.konkor.util.Constants.HONAR
import com.project.konkor.util.Constants.RIAZI
import com.project.konkor.util.Constants.TAJROBI
import com.project.konkor.util.Constants.ZABAN
import com.project.konkor.util.DataGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LessonViewModel
@Inject
constructor(
    private val sharedPreferencesCategory: SharedPreferencesAuth
) : ViewModel() {

    private var listLesson: MutableLiveData<List<LessonModel>>? = null

    fun getLessenList(): LiveData<List<LessonModel>> {

        if (listLesson == null) {
            listLesson = MutableLiveData()

            viewModelScope.launch {

                when (sharedPreferencesCategory.categoryLesson) {
                    RIAZI -> {
                        listLesson?.value = DataGenerator.getRiaziLessen()
                    }
                    TAJROBI -> {
                        listLesson?.value = DataGenerator.getTajrobiLessen()
                    }
                    ENSANI -> {
                        listLesson?.value = DataGenerator.getEnsaniLessen()
                    }
                    HONAR -> {
                        listLesson?.value = DataGenerator.getHonarLessen()
                    }
                    ZABAN -> {
                        listLesson?.value = DataGenerator.getZabanLessen()
                    }
                }

            }

        }

        return listLesson!!
    }


}