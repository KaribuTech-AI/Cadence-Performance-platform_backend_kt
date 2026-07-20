package com.cadence.performance_platform.dto

import java.util.UUID
import java.time.OffsetDateTime

data class OrganisationResponse(
    val organisationId: UUID,
    val name: String,
    val code: String,
    val status: String,
    val createdAt: OffsetDateTime
)