package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ResetPasswordRequest(
    @JsonProperty("token") val token: String,
    @JsonProperty("passwordHash") val passwordHash: String
)