package com.project.konkor.network.entity

import com.google.gson.annotations.SerializedName

data class TokenNetworkEntity(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("expiresIn")
    val expiresIn: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)