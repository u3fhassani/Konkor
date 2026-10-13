package com.project.konkor.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.konkor.model.TokenContainer
import com.project.konkor.network.ApiInterface
import com.project.konkor.network.KonkorAuthenticator
import com.project.konkor.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
//              .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideService(
        retrofit: Retrofit.Builder,
        @ApplicationContext context: Context
    ): ApiInterface {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor {
                val oldRequest = it.request()
                val newRequestBuilder = oldRequest.newBuilder()
                if (!TokenContainer.accessToken.isNullOrEmpty()) {
                    newRequestBuilder.addHeader("Authorization", "${TokenContainer.accessToken}")
                }

//                newRequestBuilder.addHeader("Accept", "Application/json")

                newRequestBuilder.method(oldRequest.method, oldRequest.body)

                return@addInterceptor it.proceed(newRequestBuilder.build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .authenticator(KonkorAuthenticator(retrofit, context))
            .build()

        return retrofit
            .client(okHttpClient)
            .build().create(ApiInterface::class.java)
    }

}