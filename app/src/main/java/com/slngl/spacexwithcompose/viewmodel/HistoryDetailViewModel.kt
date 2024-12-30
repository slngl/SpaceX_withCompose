package com.slngl.spacexwithcompose.viewmodel

import androidx.lifecycle.ViewModel
import com.slngl.spacexcompose.model.HistoryData
import com.slngl.spacexwithcompose.repository.HistoryRepository
import com.slngl.spacexwithcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel
    @Inject
    constructor(
        private val repository: HistoryRepository,
    ) : ViewModel() {
        suspend fun getHistoryDetail(id: Int): Resource<HistoryData> = repository.getHistory(id)
    }
