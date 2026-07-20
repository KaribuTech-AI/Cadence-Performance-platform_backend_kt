package com.cadence.performance_platform.dto

data class PagedAuditLogResponse(
    val content: List<AuditLogResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)