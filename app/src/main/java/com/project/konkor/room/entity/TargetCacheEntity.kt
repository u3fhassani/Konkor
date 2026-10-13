package com.project.konkor.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "target")
data class TargetCacheEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "checkBox")
    var checkBox: Boolean
)
