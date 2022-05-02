package com.shiftkey.codingchallenge

import com.shiftkey.codingchallenge.model.ShiftsPayload
import retrofit2.http.GET
import retrofit2.http.Query

interface ShiftsApi {

    @GET("available_shifts")
    suspend fun getShiftsForWeekStarting(
        @Query("type") type: String = "week",
        @Query("start") start: String? = null,
        @Query("end") end: String? = null,
        @Query("address") address: String? = null,
    ): ShiftsPayload
}