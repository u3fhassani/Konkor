package com.project.konkor.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class YearModel(
    val id: String,
    val year: Int,
    val isNewSystem: Boolean,
    val isAbroad: Boolean,
    var category: String? = null
) : Parcelable
