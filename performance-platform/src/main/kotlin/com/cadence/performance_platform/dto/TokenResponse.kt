package com.cadence.performance_platform.dto

import java.util.UUID

data class TokenResponse(
    val userId: UUID,
    val email: String,
    val displayName: String,
    val accessToken: String,
    val status: String
)