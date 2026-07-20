package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CheckinUpdateRequest(
    @JsonProperty("notes") val notes: String?,
    @JsonProperty("status") val status: String? // e.g., "SCHEDULED", "COMPLETED", "MISSED"
)
