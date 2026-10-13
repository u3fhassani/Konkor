package com.project.konkor.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "result")
data class ResultCacheEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "year")
    var year: Int,
    @ColumnInfo(name = "dateTime")
    var dateTime: String,
    @ColumnInfo(name = "lesson")
    var lesson: String,
    @ColumnInfo(name = "questionCount")
    var questionCount: Int,
    @ColumnInfo(name = "responseRate")
    var responseRate: String,
    @ColumnInfo(name = "trueAnswer")
    var trueAnswer: Int,
    @ColumnInfo(name = "falseAnswer")
    var falseAnswer: Int,
    @ColumnInfo(name = "notAnswer")
    var notAnswer: Int,
    @ColumnInfo(name = "isNewSystem")
    var isNewSystem: Boolean,
    @ColumnInfo(name = "isAbroad")
    var isAbroad: Boolean,
)
