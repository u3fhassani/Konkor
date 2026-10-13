package com.project.konkor.network.entity

import com.google.gson.annotations.SerializedName

data class AuthNetworkEntity(
    @SerializedName("age")
    val age: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
