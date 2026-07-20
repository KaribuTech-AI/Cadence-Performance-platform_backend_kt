package com.cadence.performance_platform.dto

data class PagedReviewAppraisalResponse(
    val content: List<ReviewAppraisalResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)
