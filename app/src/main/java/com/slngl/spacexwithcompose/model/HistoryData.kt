package com.slngl.spacexcompose.model

data class HistoryData(
    val details: String?,
    val eventDateUnix: Int?,
    val eventDateUtc: String?,
    val flightNumber: Int?,
    val id: Int?,
    val links: Links?,
    val title: String?,
)
