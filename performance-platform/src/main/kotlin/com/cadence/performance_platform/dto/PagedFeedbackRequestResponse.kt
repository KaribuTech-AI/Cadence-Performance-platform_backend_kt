package com.cadence.performance_platform.dto

import java.time.OffsetDateTime
import java.util.UUID

data class PagedFeedbackRequestResponse(
    val content: List<FeedbackRequestSummaryResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Int,
    val pageSize: Int
)

data class FeedbackRequestSummaryResponse(
    val requestId: UUID,
    val targetUserName: String,
    val reviewerName: String,
    val status: String,
    val deadline: OffsetDateTime
)