package com.project.konkor.network.entity

import com.google.gson.annotations.SerializedName


data class QuestionNetworkEntity(
    @SerializedName("_id")
    val id: String,
    @SerializedName("answerText")
    val answerText: String,
    @SerializedName("answerTrueNumber")
    val answerTrueNumber: Int,
    @SerializedName("category")
    val categoryLesson: String,
    @SerializedName("choice1")
    val choice1: String,
    @SerializedName("choice2")
    val choice2: String,
    @SerializedName("choice3")
    val choice3: String,
    @SerializedName("choice4")
    val choice4: String,
    @SerializedName("lesson")
    val lesson: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("questionNumber")
    val questionNumber: Int,
    @SerializedName("idYear")
    val yearsId: Int,
    @SerializedName("isNewSystem")
    val isNewSystem: Boolean,
    @SerializedName("isAbroad")
    val isAbroad: Boolean,
    @SerializedName("imageUrlQuestion")
    val imageUrlQuestion: String? = null,
    @SerializedName("imageUrlQuestionNight")
    val imageUrlQuestionNight: String? = null,
    @SerializedName("imageUrlAnswer")
    val imageUrlAnswer: String? = null,
    @SerializedName("imageUrlAnswerNight")
    val imageUrlAnswerNight: String? = null,
)