package com.cadence.performance_platform.dto

import java.util.UUID

data class OrganisationUnitResponse(
    val unitId: UUID,
    val organisationId: UUID,
    val parentUnitId: UUID?,
    val name: String,
    val code: String,
    val type: String, // e.g., DIVISION, DEPARTMENT, TEAM
    val status: String
)