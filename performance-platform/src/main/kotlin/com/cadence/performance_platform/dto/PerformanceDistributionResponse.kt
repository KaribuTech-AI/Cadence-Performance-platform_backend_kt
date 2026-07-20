package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PerformanceDistributionResponse(
    @JsonProperty("cycleId") val cycleId: java.util.UUID,
    @JsonProperty("totalCount") val totalCount: Int,
    @JsonProperty("distributionBuckets") val distributionBuckets: Map<String, Int> // e.g., "1.0-2.0" -> 15, "4.0-5.0" -> 45
)
