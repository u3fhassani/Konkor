package com.project.konkor.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.konkor.room.entity.TargetCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TargetDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(targetCacheEntity: TargetCacheEntity)

    @Delete
    suspend fun delete(targetCacheEntity: TargetCacheEntity)

    @Update
    suspend fun update(targetCacheEntity: TargetCacheEntity)

    @Query("SELECT * FROM target")
    suspend fun getAllTarget(): List<TargetCacheEntity>


}