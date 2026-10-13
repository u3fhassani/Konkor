package com.project.konkor.network.entity


import com.google.gson.annotations.SerializedName

data class UserNetworkEntity(
    @SerializedName("token")
    val token: TokenNetworkEntity,
    @SerializedName("user")
    val user: AuthNetworkEntity
)