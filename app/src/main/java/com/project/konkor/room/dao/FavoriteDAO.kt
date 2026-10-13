package com.project.konkor.room.dao

import androidx.room.*
import com.project.konkor.room.entity.FavoriteCacheEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteCacheEntity: FavoriteCacheEntity)

    @Query("SELECT * FROM favorite WHERE category=:category")
    fun getAll(category: String): Flow<List<FavoriteCacheEntity>>

    @Delete()
    suspend fun delete(favoriteCacheEntity: FavoriteCacheEntity)

    @Query("SELECT * FROM favorite WHERE idYear=:idYear AND category=:category AND lesson=:lesson AND isNewSystem=:newSystem AND isAbroad=:isAbroad")
    suspend fun getFavoriteById(
        idYear: String,
        category: String,
        lesson: String,
        newSystem: Boolean,
        isAbroad: Boolean
    ): FavoriteCacheEntity?

}