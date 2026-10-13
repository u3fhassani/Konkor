package com.project.konkor.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LessonModel(
    var id: String,
    val lessonPersianName: String,
    val lesson: String,
    val questionCount: Int,
    val time: Int,
    var idYear: String? = null,
    var isNewSystem: Boolean? = null,
    var isAbroad: Boolean? = null,
    var category: String? = null
) : Parcelable
