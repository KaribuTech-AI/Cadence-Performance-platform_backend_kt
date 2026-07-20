package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshTokenRequest(
    @JsonProperty("refreshToken") val refreshToken: String
)