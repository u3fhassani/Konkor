package com.project.konkor.di

import android.content.Context
import androidx.room.Room
import com.project.konkor.room.ApplicationDatabase
import com.project.konkor.room.dao.*
import com.project.konkor.util.Constants.QUESTION_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    
    @Singleton
    @Provides
    fun provideQuestionDB(
        @ApplicationContext context: Context): ApplicationDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ApplicationDatabase::class.java,
            QUESTION_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideTargetDao(applicationDatabase: ApplicationDatabase): TargetDAO {
        return applicationDatabase.targetDAO()
    }

    @Singleton
    @Provides
    fun provideResultDao(applicationDatabase: ApplicationDatabase): ResultDAO {
        return applicationDatabase.resultDAO()
    }

    @Singleton
    @Provides
    fun provideFavoriteDAO(applicationDatabase: ApplicationDatabase): FavoriteDAO {
        return applicationDatabase.favoriteDAO()
    }

    @Singleton
    @Provides
    fun provideMethodDAO(applicationDatabase: ApplicationDatabase): MethodDAO {
        return applicationDatabase.methodDAO()
    }

}