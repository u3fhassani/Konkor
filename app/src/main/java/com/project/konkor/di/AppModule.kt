package com.project.konkor.di

import android.content.Context
import com.project.konkor.sharedPref.SharedPreferencesAuth
import com.project.konkor.sharedPref.SharedPreferencesSetting
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideSharedPrefAuth(@ApplicationContext context: Context): SharedPreferencesAuth {
        return SharedPreferencesAuth(context)
    }

    @Singleton
    @Provides
    fun provideSharedPrefSetting(@ApplicationContext context: Context): SharedPreferencesSetting {
        return SharedPreferencesSetting(context)
    }


}