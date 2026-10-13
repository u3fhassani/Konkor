package com.project.konkor.util

import android.util.Patterns


object Constants {

    const val IP_ADDRESS = "kontest-app.ir"
    const val BASE_URL = "https://kontest-app.ir/api/v1/"

    const val CATEGORY = "category"
    const val CREATE_AT = "dateCreateAt"
    const val UPDATE_AT = "updateAt"
    const val PHOTO_EXIST = "photoExist"
    const val ID_YEAR = "idYears"
    const val AGE_USER = "ageUser"

    const val RIAZI = "riazi"
    const val TAJROBI = "tajrobi"
    const val ENSANI = "ensani"
    const val ZABAN = "zaban"
    const val HONAR = "honar"

    const val FIRST_NAME = "firstName"
    const val LAST_NAME = "lastName"
    const val EMAIL = "email"
    const val USER_ID = "accessToken"

    const val TOKEN = "token"
    const val REFRESH_TOKEN = "refreshToken"

    const val RATE = "rating"
    const val ANSWER_COUNT = "answerCount"
    const val QUESTION_COUNT = "questionCount"
    const val TRUE_COUNT = "trueCount"
    const val FALSE_COUNT = "falseCount"
    const val NOT_ANSWER_COUNT = "notAnswerCount"
    const val LESSON = "lesson"

    const val QUESTION_DATABASE_NAME = "konkor"

    const val DARK_THEME = 2
    const val DAY_THEME = 1
    const val DEFAULT_THEME = 0

    const val FONT_BADR = 1
    const val FONT_B_NAZANIN = 2
    const val FONT_MITRA = 3
    const val FONT_MORVARID = 4

    const val THEME_MODE = "themeMode"
    const val FONT_MODE = "fontMode"
    const val SCREEN_ON = "screenOn"


    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun String.persianName(): String {
        return when (this) {
            ENSANI -> "انسانی"
            RIAZI -> "ریاضی"
            TAJROBI -> "تجربی"
            HONAR -> "هنر"
            ZABAN -> "زبان"
            else -> ""
        }
    }

    fun String.englishName(): String {
        return when (this) {
            "انسانی" -> ENSANI
            "ریاضی" -> RIAZI
            "تجربی" -> TAJROBI
            "هنر" -> HONAR
            "زبان" -> ZABAN
            else -> ""
        }
    }
}