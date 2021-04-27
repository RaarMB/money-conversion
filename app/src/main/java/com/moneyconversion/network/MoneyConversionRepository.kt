package com.moneyconversion.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoneyConversionRepository @Inject constructor(
    private val apiService: ApiService,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun getConversionRate() =
        withContext(coroutineDispatcher) {
            apiService.getConversionRate()
        }

    suspend fun getConversion(from: String, to: String, amount: String) =
        withContext(coroutineDispatcher) {
            apiService.getConversion(from = from, to = to, amount = amount)
        }

    companion object {
        const val MXN_CODE = "MXN"
        const val USD_CODE = "USD"
    }
}