package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class FeedbackCreateRequest(
    @JsonProperty("receiverId") val receiverId: UUID,

    @field:NotBlank(message = "Feedback message text is required")
    @JsonProperty("message") val message: String,

    @JsonProperty("visibility") val visibility: String,
    @JsonProperty("badgeType") val badgeType: String?
)
