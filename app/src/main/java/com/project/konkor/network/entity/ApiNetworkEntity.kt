package com.project.konkor.network.entity

import com.google.gson.annotations.SerializedName

data class ApiNetworkEntity(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("success")
    val success: Boolean
)
