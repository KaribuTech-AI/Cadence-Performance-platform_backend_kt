package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class ReviewTemplateCreateRequest(
    @field:NotBlank(message = "Template name is required")
    @JsonProperty("name") val name: String,

    @JsonProperty("description") val description: String?,
    @JsonProperty("sections") val sections: List<TemplateSectionCreateRequest> = emptyList()
)
