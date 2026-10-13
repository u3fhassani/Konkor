package com.project.konkor.repository

import androidx.annotation.WorkerThread
import com.project.konkor.di.IoDispatcher
import com.project.konkor.network.ApiInterface
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.util.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentRepository
@Inject
constructor(
    private val sharedPreferencesAuth: SharedPreferencesAuth,
    private val retrofit: ApiInterface,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun sendComment(comment: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {

            val responseComment = retrofit.sendComment(
                sharedPreferencesAuth.email,
                comment
            )

            emit(DataState.Success(responseComment.success))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }.flowOn(ioDispatcher)


}