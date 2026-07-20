package com.cadence.performance_platform.dto

data class PagedUserResponse(
    val content: List<UserResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)
