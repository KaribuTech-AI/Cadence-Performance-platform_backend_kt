package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ReviewTemplateUpdateRequest(
    @JsonProperty("name") val name: String?,
    @JsonProperty("description") val description: String?,
    @JsonProperty("isActive") val isActive: Boolean?,
    @JsonProperty("components") val components: List<TemplateComponentInput>?
)
