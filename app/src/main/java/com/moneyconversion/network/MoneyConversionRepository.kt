package com.moneyconversion.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.moneyconversion.model.ConversionResult
import kotlinx.coroutines.CoroutineDispatcher
import java.lang.Exception
import javax.inject.Inject

class MoneyConversionRepository @Inject constructor(
    private val apiService: ApiService,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    fun getConversion(from: String, to: String, amount: String): LiveData<ConversionResult> =
        liveData(coroutineDispatcher) {
            try {
                val response = apiService.getConversion(from = from, to = to, amount = amount)
                when (response.success) {
                    true -> emit(ConversionResult.ConversionSuccess(response.result.toString()))
                    else -> emit(ConversionResult.ConversionError(response.error?.info.toString()))
                }
            } catch (e: Exception) {
                emit(ConversionResult.ConversionError(e.message.toString()))
            }
        }

    companion object {
        const val MXN_CODE = "MXN"
        const val USD_CODE = "USD"
    }
}