package com.project.konkor.network.entity


import com.google.gson.annotations.SerializedName

data class YearNetworkEntity(
    @SerializedName("_id")
    val id: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("isAbroad")
    val isAbroad: Boolean,
    @SerializedName("isNewSystem")
    val isNewSystem: Boolean
)