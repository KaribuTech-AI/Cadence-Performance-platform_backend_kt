package com.cadence.performance_platform.dto

data class PagedCheckinResponse(
    val content: List<CheckinResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)
