package com.project.konkor.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteCacheEntity(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @ColumnInfo(name = "lessonPersianName")
    var lessonPersianName: String,
    @ColumnInfo(name = "lesson")
    var lesson: String,
    @ColumnInfo(name = "idYear")
    var idYear: String,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "questionCount")
    var questionCount: Int,
    @ColumnInfo(name = "time")
    var time: Int,
    @ColumnInfo(name = "isNewSystem")
    var isNewSystem: Boolean,
    @ColumnInfo(name = "isAbroad")
    var isAbroad: Boolean
)