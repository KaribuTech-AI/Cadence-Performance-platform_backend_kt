package com.cadence.performance_platform.dto

import java.util.UUID

data class OrganisationUnitTreeResponse(
    val unitId: UUID,
    val name: String,
    val code: String,
    val type: String,
    val status: String,
    val children: List<OrganisationUnitTreeResponse> = emptyList()
)