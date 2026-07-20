package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FeatureToggleUpdateRequest(
    @JsonProperty("isEnabled") val isEnabled: Boolean
)
