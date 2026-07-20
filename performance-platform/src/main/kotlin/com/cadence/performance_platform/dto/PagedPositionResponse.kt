package com.cadence.performance_platform.dto

data class PagedPositionResponse(
    val content: List<PositionResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)
