package com.moneyconversion.network

import com.moneyconversion.BuildConfig
import com.moneyconversion.model.ConversionResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET
    suspend fun getConversion(
        @Query("access_key") accessKey: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: String
    ): ConversionResponse

    companion object {
        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BuildConfig.SERVER_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}