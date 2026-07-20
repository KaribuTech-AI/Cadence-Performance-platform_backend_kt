package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OrganisationUpdateRequest(
    @JsonProperty("name") val name: String?,
    @JsonProperty("status") val status: String?
)