package com.cadence.performance_platform.dto

data class PagedReviewTemplateResponse(
    val content: List<ReviewTemplateResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)
