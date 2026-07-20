package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LogoutRequest(
    @JsonProperty("refreshToken") val refreshToken: String
)