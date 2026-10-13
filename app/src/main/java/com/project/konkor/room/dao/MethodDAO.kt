package com.project.konkor.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.konkor.room.entity.MethodCacheEntity

@Dao
interface MethodDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(methodCacheEntity: MethodCacheEntity)

    @Query("SELECT * FROM method")
    suspend fun getAll():List<MethodCacheEntity>

}