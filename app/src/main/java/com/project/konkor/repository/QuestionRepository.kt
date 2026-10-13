package com.project.konkor.repository

import androidx.annotation.WorkerThread
import com.project.konkor.di.IoDispatcher
import com.project.konkor.model.LessonModel
import com.project.konkor.model.QuestionModel
import com.project.konkor.model.ResultModel
import com.project.konkor.network.ApiInterface
import com.project.konkor.network.mapper.QuestionNetworkMapper
import com.project.konkor.room.dao.FavoriteDAO
import com.project.konkor.room.dao.ResultDAO
import com.project.konkor.room.mapper.FavoriteCacheMapper
import com.project.konkor.room.mapper.ResultCacheMapper
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.util.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuestionRepository
@Inject
constructor(
    private val retrofit: ApiInterface,
    private val resultDAO: ResultDAO,
    private val favoriteDAO: FavoriteDAO,
    private val questionNetworkMapper: QuestionNetworkMapper,
    private val resultCacheMapper: ResultCacheMapper,
    private val favoriteCacheMapper: FavoriteCacheMapper,
    private val sharedPrefAuth: SharedPreferencesAuth,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getQuestionFromNetwork(
        idYear: String,
        category: String,
        lesson: String,
        isNewSystem: Boolean,
        isAbroad: Boolean
    ): Flow<DataState<List<QuestionModel>>> = flow {
        emit(DataState.Loading)

        try {

            val getQuestion = retrofit.getQuestionByLessen(idYear, category, lesson, isNewSystem, isAbroad)

            val questions = questionNetworkMapper.mapFromEntityList(getQuestion)


            emit(DataState.Success(questions))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }.flowOn(ioDispatcher)


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertResult(resultModel: ResultModel) {
        withContext(ioDispatcher) {
            val cacheResult = resultCacheMapper.mapToEntity(resultModel)
            resultDAO.insert(cacheResult)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFavorite(lessonModel: LessonModel) {
        withContext(ioDispatcher) {
            val cacheFavorite = favoriteCacheMapper.mapToEntity(lessonModel)
            favoriteDAO.insert(cacheFavorite)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteFavorite(lessonModel: LessonModel) {
        withContext(ioDispatcher) {
            val cacheFavorite = favoriteCacheMapper.mapToEntity(lessonModel)
            favoriteDAO.delete(cacheFavorite)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getAllFavorite(): Flow<List<LessonModel>> = flow {
        favoriteDAO.getAll(sharedPrefAuth.categoryLesson).collect {
            val listModel = favoriteCacheMapper.mapFromEntityList(it)
            emit(listModel)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getFavoriteById(
        idYear: String,
        category: String,
        lesson: String,
        isNewSystem: Boolean,
        isAbroad: Boolean
    ): Flow<Boolean> =
        flow {

            val cacheEntity =
                favoriteDAO.getFavoriteById(idYear, category, lesson, isNewSystem, isAbroad)

            if (cacheEntity == null) {
                emit(false)
            } else {
                emit(true)
            }


        }

}