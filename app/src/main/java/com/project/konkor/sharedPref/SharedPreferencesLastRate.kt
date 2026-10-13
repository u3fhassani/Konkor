package com.project.konkor.sharedPref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.project.konkor.util.Constants.ANSWER_COUNT
import com.project.konkor.util.Constants.FALSE_COUNT
import com.project.konkor.util.Constants.LESSON
import com.project.konkor.util.Constants.NOT_ANSWER_COUNT
import com.project.konkor.util.Constants.QUESTION_COUNT
import com.project.konkor.util.Constants.RATE
import com.project.konkor.util.Constants.TRUE_COUNT

class SharedPreferencesLastRate(context: Context) {

    private val sharedPreferencesLastRate: SharedPreferences =
        context.getSharedPreferences("lastRate", MODE_PRIVATE)


    fun saveRate(rate: Float) {
        sharedPreferencesLastRate.edit {
            putFloat(RATE, rate)
        }
    }

    fun saveAnswerCount(answerCount: Int) {
        sharedPreferencesLastRate.edit {
            putInt(ANSWER_COUNT, answerCount)
        }
    }

    fun saveQuestionCount(questionCount: Int) {
        sharedPreferencesLastRate.edit {
            putInt(QUESTION_COUNT, questionCount)
        }
    }

    fun saveTrueCount(trueCount: Int) {
        sharedPreferencesLastRate.edit {
            putInt(TRUE_COUNT, trueCount)
        }
    }

    fun saveFalseCount(falseCount: Int) {
        sharedPreferencesLastRate.edit {
            putInt(FALSE_COUNT, falseCount)
        }
    }

    fun saveNotAnswerCount(notAnswerCount: Int) {
        sharedPreferencesLastRate.edit {
            putInt(NOT_ANSWER_COUNT, notAnswerCount)
        }
    }

    fun saveLesson(lesson: String) {
        sharedPreferencesLastRate.edit {
            putString(LESSON, lesson)
        }
    }


    val rate: Float
        get() = sharedPreferencesLastRate.getFloat(RATE, 0f)

    val answerCount: Int
        get() = sharedPreferencesLastRate.getInt(ANSWER_COUNT, 0)

    val questionCount: Int
        get() = sharedPreferencesLastRate.getInt(QUESTION_COUNT, 0)

    val trueCount: Int
        get() = sharedPreferencesLastRate.getInt(TRUE_COUNT, 0)

    val falseCount: Int
        get() = sharedPreferencesLastRate.getInt(FALSE_COUNT, 0)

    val notAnswerCount: Int
        get() = sharedPreferencesLastRate.getInt(NOT_ANSWER_COUNT, 0)

    val lesson: String
        get() = sharedPreferencesLastRate.getString(LESSON, "")!!


}