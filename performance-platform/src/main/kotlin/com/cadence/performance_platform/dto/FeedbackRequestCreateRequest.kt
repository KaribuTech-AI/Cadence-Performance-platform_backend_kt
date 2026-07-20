package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class FeedbackRequestCreateRequest(
    @field:NotNull(message = "Target employee ID is required")
    @JsonProperty("targetUserId") val targetUserId: UUID,

    @field:NotEmpty(message = "At least one reviewer must be selected")
    @JsonProperty("reviewerIds") val reviewerIds: List<UUID>,

    @field:NotNull(message = "Template ID is required to bind questionnaire structure")
    @JsonProperty("templateId") val templateId: UUID
)
