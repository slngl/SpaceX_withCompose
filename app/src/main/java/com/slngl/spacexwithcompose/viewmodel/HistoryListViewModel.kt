package com.slngl.spacexwithcompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slngl.spacexcompose.model.HistoryListItem
import com.slngl.spacexwithcompose.repository.HistoryRepository
import com.slngl.spacexwithcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryListViewModel
    @Inject
    constructor(
        private val repository: HistoryRepository,
    ) : ViewModel() {
        var historyList = mutableStateOf<List<HistoryListItem>>(listOf())
        var errorMessage = mutableStateOf("")
        var isLoading = mutableStateOf(false)

        // for searching
        private var initialHistoryList = listOf<HistoryListItem>()
        private var isSearchStarting = true

        init {
            loadHistories()
        }

        fun loadHistories() {
            viewModelScope.launch {
                isLoading.value = true
                val result = repository.getHistoryList()
                when (result) {
                    is Resource.Success -> {
                        val historyItems =
                            result.data!!.mapIndexed { _, historyData ->
                                HistoryListItem(
                                    id = historyData.id,
                                    title = historyData.title,
                                    details = historyData.details,
                                )
                            }
                        historyList.value = historyItems
                        initialHistoryList = historyItems
                        errorMessage.value = ""
                        isLoading.value = false
                    }

                    is Resource.Error -> {
                        errorMessage.value = result.message ?: "viewmodel historylist error"
                        isLoading.value = false
                    }

                    is Resource.Loading -> {
                        errorMessage.value = ""
                    }
                }
            }
        }

        fun searchHistoryList(query: String) {
            val listToSearch =
                if (isSearchStarting) {
                    historyList.value
                } else {
                    initialHistoryList
                }

            viewModelScope.launch(Dispatchers.Default) {
                if (query.isEmpty()) {
                    historyList.value = initialHistoryList
                    isSearchStarting = true
                    return@launch
                }

                val results =
                    listToSearch.filter {
                        it.title!!.contains(query.trim(), ignoreCase = true)
                    }

                if (isSearchStarting) {
                    initialHistoryList = historyList.value
                    isSearchStarting = false
                }
                historyList.value = results
            }
        }
    }
