package com.moneyconversion

import com.moneyconversion.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun providesApiService() = ApiService.create()

    @Provides
    fun coroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

}