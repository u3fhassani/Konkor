package com.project.konkor.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Entity(tableName = "method")
data class MethodCacheEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "methodTitle")
    val methodTitle: String,
    @ColumnInfo(name = "methodDescription")
    val methodDescription: String,
    @ColumnInfo(name = "lottieAnimation")
    val lottieAnimation: String,
    @ColumnInfo(name = "methodCat")
    val methodCat: String
)