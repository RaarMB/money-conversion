package com.moneyconversion

import com.moneyconversion.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providesApiService() = ApiService.create()

    @Singleton
    @Provides
    fun coroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

}