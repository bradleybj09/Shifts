package com.shiftkey.codingchallenge

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class WebModule {

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://staging-app.shiftkey.com/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun shiftsApi(retrofit: Retrofit): ShiftsApi = retrofit.create(ShiftsApi::class.java)

    @Provides
    fun coroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)
}