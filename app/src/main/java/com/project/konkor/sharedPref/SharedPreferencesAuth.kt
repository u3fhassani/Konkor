package com.project.konkor.sharedPref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.project.konkor.model.TokenContainer
import com.project.konkor.util.Constants.AGE_USER
import com.project.konkor.util.Constants.CATEGORY
import com.project.konkor.util.Constants.CREATE_AT
import com.project.konkor.util.Constants.EMAIL
import com.project.konkor.util.Constants.FIRST_NAME
import com.project.konkor.util.Constants.LAST_NAME
import com.project.konkor.util.Constants.PHOTO_EXIST
import com.project.konkor.util.Constants.REFRESH_TOKEN
import com.project.konkor.util.Constants.TOKEN
import com.project.konkor.util.Constants.UPDATE_AT


class SharedPreferencesAuth(context: Context) {
    private val sharedPreferencesAuth: SharedPreferences =
        context.getSharedPreferences("authInfo", MODE_PRIVATE)


    fun saveFirstName(firstName: String) {
        sharedPreferencesAuth.edit {
            putString(FIRST_NAME, firstName)
        }
    }

    fun saveLastName(lastName: String) {
        sharedPreferencesAuth.edit {
            putString(LAST_NAME, lastName)
        }
    }

    fun saveEmail(email: String) {
        sharedPreferencesAuth.edit {
            putString(EMAIL, email)
        }
    }

    fun saveCategoryLessen(category: String) {
        sharedPreferencesAuth.edit {
            putString(CATEGORY, category)
        }
    }

    fun saveDateCreateAt(createAt: String) {
        sharedPreferencesAuth.edit {
            putString(CREATE_AT, createAt)
        }
    }

    fun saveAgeUser(age: Int) {
        sharedPreferencesAuth.edit {
            putInt(AGE_USER, age)
        }
    }

    fun saveDateUpdateAt(updateAt: String) {
        sharedPreferencesAuth.edit {
            putString(UPDATE_AT, updateAt)
        }
    }

    fun savePhotoExist(isExist: Boolean) {
        sharedPreferencesAuth.edit {
            putBoolean(PHOTO_EXIST, isExist)
        }
    }

    fun saveAccessToken(token: String) {
        sharedPreferencesAuth.edit {
            putString(TOKEN, token)
        }
    }

    fun saveRefreshToken(refreshToken: String) {
        sharedPreferencesAuth.edit {
            putString(REFRESH_TOKEN, refreshToken)
        }
    }

    val accessToken: String
        get() = sharedPreferencesAuth.getString(TOKEN, "") ?: ""

    val refreshToken: String
        get() = sharedPreferencesAuth.getString(REFRESH_TOKEN, "") ?: ""

    val isPhotoExist: Boolean
        get() = sharedPreferencesAuth.getBoolean(PHOTO_EXIST, false)

    val firstName: String
        get() = sharedPreferencesAuth.getString(FIRST_NAME, "") ?: ""

    val lastName: String
        get() = sharedPreferencesAuth.getString(LAST_NAME, "") ?: ""

    val email: String
        get() = sharedPreferencesAuth.getString(EMAIL, "") ?: ""

    val categoryLesson: String
        get() = sharedPreferencesAuth.getString(CATEGORY, "") ?: ""

    val dateCreateAt: String
        get() = sharedPreferencesAuth.getString(CREATE_AT, ",,") ?: ",,"

    val updateAt: String
        get() = sharedPreferencesAuth.getString(UPDATE_AT, "") ?: ""

    val ageUser: Int
        get() = sharedPreferencesAuth.getInt(AGE_USER, 0)

    fun signOut() {
        sharedPreferencesAuth.edit {
            clear()
        }
        TokenContainer.update(null, null)
    }

}