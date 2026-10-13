package com.project.konkor.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionModel(
    var id: String,
    var answerText: String,
    var answerTrueNumber: Int,
    var categoryLesson: String,
    var choice1: String,
    var choice2: String,
    var choice3: String,
    var choice4: String,
    var lesson: String,
    var question: String,
    var questionNumber: Int,
    var yearsId: Int,
    var isNewSystem: Boolean,
    var isAbroad: Boolean,
    var imageUrlQuestion: String? = null,
    var imageUrlQuestionNight: String? = null,
    var imageUrlAnswer: String? = null,
    var imageUrlAnswerNight: String? = null,
    var choice: Int = 0
) : Parcelable