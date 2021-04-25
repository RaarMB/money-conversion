package com.moneyconversion.model

sealed class ConversionResult {
    data class ConversionSuccess(val result: String) : ConversionResult()
    data class ConversionError(val errorMessage: String) : ConversionResult()
}