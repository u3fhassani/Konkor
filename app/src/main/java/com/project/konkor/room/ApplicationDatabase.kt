package com.project.konkor.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.konkor.room.dao.*
import com.project.konkor.room.entity.*

@Database(
    entities = [
        TargetCacheEntity::class,
        ResultCacheEntity::class,
        FavoriteCacheEntity::class,
        MethodCacheEntity::class,
    ], version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun targetDAO(): TargetDAO

    abstract fun resultDAO(): ResultDAO

    abstract fun favoriteDAO(): FavoriteDAO

    abstract fun methodDAO(): MethodDAO
}