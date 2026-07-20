package com.cadence.performance_platform.dto

data class PagedCompetencyResponse(
    val content: List<CompetencyResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)
