package com.slngl.spacexwithcompose.network

import com.slngl.spacexcompose.model.HistoryList
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoryService {
    @GET("history")
    suspend fun getHistory(
        @Query("id") id: Int? = null,
    ): HistoryList
}
