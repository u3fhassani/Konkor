package com.project.konkor.network.entity


import com.google.gson.annotations.SerializedName

data class MethodNetworkEntity(
    @SerializedName("_id")
    val id: String,
    @SerializedName("lottieAnimation")
    val lottieAnimation: String,
    @SerializedName("methodCat")
    val methodCat: String,
    @SerializedName("methodTitle")
    val methodTitle: String,
    @SerializedName("methodDescription")
    val methodDescription: String
)