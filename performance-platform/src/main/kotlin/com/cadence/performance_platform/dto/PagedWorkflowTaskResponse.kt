package com.cadence.performance_platform.dto

data class PagedWorkflowTaskResponse(
    val content: List<WorkflowTaskResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)
