package com.cadence.performance_platform.dto

data class PagedGoalResponse(
    val content: List<GoalResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)
