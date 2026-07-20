package com.cadence.performance_platform.dto

import java.util.UUID

data class UserSessionResponse(
    val userId: UUID,
    val email: String,
    val displayName: String,
    val role: String,
    val status: String
)