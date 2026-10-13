package com.project.konkor.model

import androidx.annotation.DrawableRes

data class CategoryModel(
    val id :Int,
    val nameCat:String,
    val persianName :String,
    @DrawableRes val imageResource: Int
)
