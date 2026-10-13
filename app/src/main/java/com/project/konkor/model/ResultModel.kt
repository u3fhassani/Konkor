package com.project.konkor.model


data class ResultModel(
    var id: String,
    var category: String,
    var year: Int,
    var lesson: String,
    var questionCount: Int,
    var responseRate: String,
    var trueAnswer: Int,
    var falseAnswer: Int,
    var notAnswer: Int,
    var dateTime: String,
    var isNewSystem: Boolean,
    var isAbroad: Boolean
)
