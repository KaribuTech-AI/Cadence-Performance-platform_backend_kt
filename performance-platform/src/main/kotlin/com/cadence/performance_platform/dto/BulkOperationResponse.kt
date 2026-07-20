package com.cadence.performance_platform.dto

import java.util.UUID

data class BulkOperationResponse(
    val operationId: UUID,
    val status: String, // e.g., "ACCEPTED", "PROCESSING"
    val affectedRecordsCount: Int,
    val message: String
)
