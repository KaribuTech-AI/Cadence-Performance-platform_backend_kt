package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OrganisationUnitUpdateRequest(
    @JsonProperty("name") val name: String?,
    @JsonProperty("parentUnitId") val parentUnitId: java.util.UUID?,
    @JsonProperty("status") val status: String?
)