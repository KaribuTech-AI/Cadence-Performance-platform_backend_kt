package com.cadence.performance_platform.dto

data class PagedReviewCycleResponse(
    val content: List<ReviewCycleResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)
