package com.project.konkor.network

import com.project.konkor.network.entity.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("question/getQuestionByLesson")
    suspend fun getQuestionByLessen(
        @Query("idYear") idYear: String,
        @Query("category") category: String,
        @Query("lesson") lesson: String,
        @Query("isNewSystem") isNewSystem: Boolean,
        @Query("isAbroad") isAbroad: Boolean
    ): List<QuestionNetworkEntity>

    @GET("question/getQuestionByLesson")
    suspend fun getQuestions(
        @Query("idYear") idYear: String,
        @Query("category") category: String,
        @Query("isNewSystem") isNewSystem: Boolean,
        @Query("isAbroad") isAbroad: Boolean
    ): List<QuestionNetworkEntity>

    @GET("getMethod")
    suspend fun getAllMethod(): List<MethodNetworkEntity>

    @FormUrlEncoded
    @POST("user/refreshToken")
    fun refreshToken(
        @Field("refreshToken") refreshToken: String
    ): Call<TokenNetworkEntity>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun loginMethod(
        @Field("email") email: String,
        @Field("password") password: String,
    ): UserNetworkEntity


    @FormUrlEncoded
    @POST("user/register")
    suspend fun registerMethod(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): UserNetworkEntity

    @FormUrlEncoded
    @POST("user/editCategory")
    suspend fun saveCategoryMethod(
        @Field("email") email: String,
        @Field("category") category: String
    ): ApiNetworkEntity


    @FormUrlEncoded
    @POST("user/forgotPassword")
    suspend fun forgetPassword(
        @Field("email") email: String
    ): ApiNetworkEntity

    @FormUrlEncoded
    @POST("user/editPassword")
    suspend fun editPassword(
        @Field("newPassword") newPassword: String,
        @Field("oldPassword") oldPassword: String,
        @Field("email") email: String
    ): ApiNetworkEntity

    @FormUrlEncoded
    @POST("user/editProfile")
    suspend fun editProfile(
        @Field("email") email: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("age") age: Int,
        @Field("category") category: String
    ): AuthNetworkEntity

    @FormUrlEncoded
    @POST("sendComment")
    suspend fun sendComment(
        @Field("email") email: String,
        @Field("comment") comment: String
    ): ApiNetworkEntity

    @GET("year/years")
    suspend fun getYears(): List<YearNetworkEntity>

//    @FormUrlEncoded
//    @POST("Question/getAll")
//    suspend fun getPostQuestion(
//        @Field("idYear") idYear: String,
//        @Field("category") category: String,
//        @Body() test :JSONArray
//    ): List<QuestionNetworkEntity>


}
