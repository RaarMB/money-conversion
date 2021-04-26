package com.moneyconversion.model

import com.google.gson.annotations.SerializedName

data class ConversionResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val result: Double?,
    @SerializedName("error")
    val error: Error?
)

data class Error(
    @SerializedName("code")
    val code: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("info")
    val info: String
)