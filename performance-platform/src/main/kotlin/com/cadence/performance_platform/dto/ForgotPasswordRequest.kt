package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ForgotPasswordRequest(
    @JsonProperty("email") val email: String
)