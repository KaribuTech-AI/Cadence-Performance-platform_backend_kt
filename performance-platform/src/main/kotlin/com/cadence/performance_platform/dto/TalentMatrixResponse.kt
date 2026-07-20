package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class TalentMatrixResponse(
    @JsonProperty("employeeId") val employeeId: UUID,
    @JsonProperty("fullName") val fullName: String,
    @JsonProperty("performanceScore") val performanceScore: Double,
    @JsonProperty("potentialScore") val potentialScore: Double,
    @JsonProperty("placementGrid") val placementGrid: String // e.g., "HIGH_PERFORMANCE_HIGH_POTENTIAL"
)
