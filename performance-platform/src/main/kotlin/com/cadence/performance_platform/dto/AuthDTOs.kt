
package com.cadence.performance.dto

import java.util.UUID

/**
 * Request body sent by the user when logging in (AUTH-001)
 */
data class LoginRequest(
    val email: String,
    val passwordHash: String
)

/**
 * Response body returned to the user upon a successful login
 */
data class TokenResponse(
    val userId: UUID,
    val email: String,
    val displayName: String,
    val accessToken: String,
    val status: String
)
