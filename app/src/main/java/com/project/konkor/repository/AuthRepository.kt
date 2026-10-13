package com.project.konkor.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.project.konkor.di.IoDispatcher
import com.project.konkor.model.AuthModel
import com.project.konkor.model.TokenContainer
import com.project.konkor.network.ApiInterface
import com.project.konkor.network.mapper.AuthNetworkMapper
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.util.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    private val sharedPreferencesAuth: SharedPreferencesAuth,
    private val authNetworkMapper: AuthNetworkMapper,
    private val retrofit: ApiInterface,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun signIn(
        email: String,
        password: String
    ): Flow<DataState<AuthModel>> = flow {

        emit(DataState.Loading)

        try {

            val (token, loginModel) = retrofit.loginMethod(email, password)


            sharedPreferencesAuth.apply {
                saveEmail(loginModel.email)
                saveFirstName(loginModel.firstName)
                saveLastName(loginModel.lastName)
                saveCategoryLessen(loginModel.category)
                saveAgeUser(loginModel.age)
                saveDateCreateAt(loginModel.createdAt)
                saveAccessToken(token.accessToken)
                saveRefreshToken(token.refreshToken)
            }

            TokenContainer.update(token.accessToken, token.refreshToken)

            val authModel = authNetworkMapper.mapFromEntity(loginModel)

            emit(DataState.Success(authModel))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }


    }.flowOn(ioDispatcher)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ): Flow<DataState<AuthModel>> = flow {
        emit(DataState.Loading)

        try {

            val (token, loginModel) = retrofit.registerMethod(firstName, lastName, email, password)



            sharedPreferencesAuth.apply {
                saveEmail(loginModel.email)
                saveFirstName(loginModel.firstName)
                saveLastName(loginModel.lastName)
                saveCategoryLessen(loginModel.category)
                saveAgeUser(loginModel.age)
                saveDateCreateAt(loginModel.createdAt)
                saveAccessToken(token.accessToken)
                saveRefreshToken(token.refreshToken)
            }

            TokenContainer.update(token.accessToken, token.refreshToken)

            val authModel = authNetworkMapper.mapFromEntity(loginModel)

            emit(DataState.Success(authModel))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }.flowOn(ioDispatcher)


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun saveCategory(category: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)

        try {

            val catEntity = retrofit.saveCategoryMethod(sharedPreferencesAuth.email, category)

            sharedPreferencesAuth.saveCategoryLessen(category)

            emit(DataState.Success(catEntity.success))


        } catch (e: Exception) {
            emit(DataState.Error(e))
        }


    }.flowOn(ioDispatcher)


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun forgetPassword(email: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)

        try {

            val catchEntity = retrofit.forgetPassword(email)

            emit(DataState.Success(catchEntity.success))


        } catch (e: Exception) {
            Log.d("forgetPassword", "${e.message}")
            emit(DataState.Error(e))
        }

    }.flowOn(ioDispatcher)


}