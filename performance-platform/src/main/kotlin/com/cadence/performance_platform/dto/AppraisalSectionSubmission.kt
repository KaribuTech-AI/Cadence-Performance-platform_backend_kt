package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class AppraisalSectionSubmission(
    @JsonProperty("sectionId") val sectionId: UUID,
    @JsonProperty("comment") val comment: String?,
    @JsonProperty("rating") val rating: Double?
)
