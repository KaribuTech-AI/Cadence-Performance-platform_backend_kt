
package com.cadence.performance_platform.dto


import java.util.UUID

data class LoginRequest(
    val email: String,
    val passwordHash: String
)
