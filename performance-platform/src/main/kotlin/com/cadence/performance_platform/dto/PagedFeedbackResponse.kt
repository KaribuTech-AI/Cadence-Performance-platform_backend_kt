package com.cadence.performance_platform.dto

data class PagedFeedbackResponse(
    val content: List<FeedbackResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)
