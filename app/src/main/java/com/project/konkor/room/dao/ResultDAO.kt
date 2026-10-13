package com.project.konkor.room.dao

import androidx.room.*
import com.project.konkor.room.entity.ResultCacheEntity

@Dao
interface ResultDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resultCacheEntity: ResultCacheEntity)

    @Delete
    suspend fun delete(resultCacheEntity: ResultCacheEntity)

    @Query("SELECT * FROM result")
    suspend fun getAll(): List<ResultCacheEntity>

    @Query("SELECT * FROM result WHERE category=:category")
    suspend fun getByCategory(category: String): List<ResultCacheEntity>

    @Query("SELECT * FROM result WHERE category=:category AND year=:year")
    suspend fun getByCategoryAndYear(category: String, year: Int): List<ResultCacheEntity>


}