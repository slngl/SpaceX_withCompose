package com.slngl.spacexwithcompose.repository

import com.slngl.spacexcompose.model.HistoryData
import com.slngl.spacexcompose.model.HistoryList
import com.slngl.spacexwithcompose.network.HistoryService
import com.slngl.spacexwithcompose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class HistoryRepository
    @Inject
    constructor(
        private val api: HistoryService,
    ) {
        suspend fun getHistory(id: Int): Resource<HistoryData> {
            val response =
                try {
                    api.getHistory(id)
                } catch (e: Exception) {
                    return Resource.Error("getHistory error")
                }
            return Resource.Success(response.first())
        }

        suspend fun getHistoryList(): Resource<HistoryList> {
            val response =
                try {
                    api.getHistory()
                } catch (e: Exception) {
                    return Resource.Error("getHistoryList error")
                }
            return Resource.Success(response)
        }
    }
