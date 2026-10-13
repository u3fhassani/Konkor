package com.project.konkor.repository

import androidx.annotation.WorkerThread
import com.project.konkor.di.IoDispatcher
import com.project.konkor.model.ResultModel
import com.project.konkor.room.dao.ResultDAO
import com.project.konkor.room.mapper.ResultCacheMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResultRepository
@Inject
constructor(
    private val resultCacheMapper: ResultCacheMapper,
    private val resultDAO: ResultDAO,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertResult(resultModel: ResultModel) {
        withContext(ioDispatcher) {
            val cacheModel = resultCacheMapper.mapToEntity(resultModel)
            resultDAO.insert(cacheModel)
        }
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteResult(resultModel: ResultModel) {
        withContext(ioDispatcher) {
            val cacheModel = resultCacheMapper.mapToEntity(resultModel)
            resultDAO.delete(cacheModel)
        }
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getAllResult(): Flow<List<ResultModel>> = flow {
        val allResultEntity = resultDAO.getAll()
        val allResultModel = resultCacheMapper.mapFromEntityList(allResultEntity)
        emit(allResultModel)
    }.flowOn(ioDispatcher)
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getByCategory(category: String): Flow<List<ResultModel>> = flow {
        val allResultEntity = resultDAO.getByCategory(category)
        val allResultModel = resultCacheMapper.mapFromEntityList(allResultEntity)
        emit(allResultModel)
    }.flowOn(ioDispatcher)
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getByCategoryAndYear(category: String, year: Int): Flow<List<ResultModel>> = flow {
        val allResultEntity = resultDAO.getByCategoryAndYear(category, year)
        val allResultModel = resultCacheMapper.mapFromEntityList(allResultEntity)
        emit(allResultModel)
    }.flowOn(ioDispatcher)


}