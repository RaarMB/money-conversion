package com.moneyconversion.model

import com.google.gson.annotations.SerializedName

data class ConversionResponse(
    @SerializedName("query")
    val query: Query
)

data class Query(
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String,
    @SerializedName("amount")
    val amount: Int
)