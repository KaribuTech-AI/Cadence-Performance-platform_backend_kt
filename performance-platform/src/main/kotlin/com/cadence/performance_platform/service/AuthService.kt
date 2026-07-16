package com.cadence.performance_platform.service

import com.cadence.performance.dto.LoginRequest
import com.cadence.performance.dto.TokenResponse
import com.cadence.performance_platform.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthService(private val userRepository: UserRepository) {

    fun authenticate(request: LoginRequest): TokenResponse {
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { NoSuchElementException("User with email ${request.email} not found") }

        if (user.passwordHash != request.passwordHash) {
            throw IllegalArgumentException("Invalid password credentials")
        }

        val mockToken = "mock-jwt-token-for-${user.email}"

        return TokenResponse(
            userId = user.userId ?: UUID.randomUUID(),
            email = user.email,
            displayName = user.displayName ?: "${user.firstName} ${user.lastName}",
            accessToken = mockToken,
            status = user.status
        )
    }
}