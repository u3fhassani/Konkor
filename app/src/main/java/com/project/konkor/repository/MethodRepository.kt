package com.project.konkor.repository

import androidx.annotation.WorkerThread
import com.project.konkor.di.IoDispatcher
import com.project.konkor.model.MethodModel
import com.project.konkor.network.ApiInterface
import com.project.konkor.network.mapper.MethodNetworkMapper
import com.project.konkor.room.dao.MethodDAO
import com.project.konkor.room.mapper.MethodCacheMapper
import com.project.konkor.util.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MethodRepository
@Inject
constructor(
    private val retrofit: ApiInterface,
    private val methodNetworkMapper: MethodNetworkMapper,
    private val methodCacheMapper: MethodCacheMapper,
    private val methodDAO: MethodDAO,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getMethodFromServer():Flow<DataState<List<MethodModel>>> = flow {
        emit(DataState.Loading)

        try {

            val methodNetworkList = retrofit.getAllMethod()
            val methodModelList = methodNetworkMapper.mapFromEntityList(methodNetworkList)


            methodModelList.forEach { methodModel: MethodModel ->
                methodDAO.insert(methodCacheMapper.mapToEntity(methodModel))
            }

            emit(DataState.Success(methodModelList))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }.flowOn(ioDispatcher)


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getMethodFromDB():Flow<List<MethodModel>> = flow {
        val methodCacheList = methodDAO.getAll()
        val methodModelList = methodCacheMapper.mapFromEntityList(methodCacheList)
        emit(methodModelList)
    }.flowOn(ioDispatcher)




}