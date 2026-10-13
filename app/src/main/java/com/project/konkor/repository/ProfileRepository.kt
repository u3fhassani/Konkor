package com.project.konkor.repository

import androidx.annotation.WorkerThread
import com.project.konkor.di.IoDispatcher
import com.project.konkor.model.AuthModel
import com.project.konkor.model.TargetModel
import com.project.konkor.network.ApiInterface
import com.project.konkor.network.entity.ApiNetworkEntity
import com.project.konkor.network.entity.AuthNetworkEntity
import com.project.konkor.network.mapper.AuthNetworkMapper
import com.project.konkor.room.dao.TargetDAO
import com.project.konkor.room.mapper.TargetCacheMapper
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.util.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepository
@Inject
constructor(
    private val targetDAO: TargetDAO,
    private val targetCacheMapper: TargetCacheMapper,
    private val retrofit: ApiInterface,
    private val authNetworkMapper: AuthNetworkMapper,
    private val sharedPrefAuth: SharedPreferencesAuth,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTarget(targetModel: TargetModel) {
        withContext(ioDispatcher) {
            val cacheEntity = targetCacheMapper.mapToEntity(targetModel)
            targetDAO.insert(cacheEntity)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateTarget(targetModel: TargetModel) {
        withContext(ioDispatcher) {
            val cacheEntity = targetCacheMapper.mapToEntity(targetModel)
            targetDAO.update(cacheEntity)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteTarget(targetModel: TargetModel) {
        withContext(ioDispatcher) {
            val cacheEntity = targetCacheMapper.mapToEntity(targetModel)
            targetDAO.delete(cacheEntity)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getAllTarget(): Flow<List<TargetModel>> = flow {
        val allTarget = targetDAO.getAllTarget()
        val targetList = targetCacheMapper.mapFromEntityList(allTarget)
        emit(targetList)
    }.flowOn(ioDispatcher)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun editProfile(
        firstName: String,
        lastName: String,
        age: Int,
        category: String
    ): Flow<DataState<AuthModel>> =
        flow {
            emit(DataState.Loading)

            try {

                val authNetworkEntity: AuthNetworkEntity =
                    retrofit.editProfile(sharedPrefAuth.email, firstName, lastName, age, category)

                val authModel: AuthModel = authNetworkMapper.mapFromEntity(authNetworkEntity)

                sharedPrefAuth.apply {
                    saveFirstName(authModel.firstName)
                    saveLastName(authModel.lastName)
                    saveAgeUser(authModel.age)
                    saveDateUpdateAt(authModel.updatedAt)
                    saveCategoryLessen(authModel.category)
                }
                emit(DataState.Success(authModel))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }.flowOn(ioDispatcher)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun editPassword(oldPassword: String, newPassword: String): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)
            try {

                val apiNetwork: ApiNetworkEntity =
                    retrofit.editPassword(newPassword, oldPassword, sharedPrefAuth.email)

                emit(DataState.Success(apiNetwork.success))

            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }.flowOn(ioDispatcher)


}