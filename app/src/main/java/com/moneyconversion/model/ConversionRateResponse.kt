package com.moneyconversion.model

import com.google.gson.annotations.SerializedName

data class ConversionRateResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("date")
    val date: String?,
    @SerializedName("rates")
    val rates: Rates?,
    @SerializedName("error")
    val error: Error?
)

data class Rates(
    @SerializedName("USD")
    val usd: Double
)