package com.cadence.performance_platform.service


import com.cadence.performance_platform.dto.ForgotPasswordRequest
import com.cadence.performance_platform.dto.LoginRequest
import com.cadence.performance_platform.dto.LogoutRequest
import com.cadence.performance_platform.dto.MessageResponse
import com.cadence.performance_platform.dto.RefreshTokenRequest
import com.cadence.performance_platform.dto.ResetPasswordRequest
import com.cadence.performance_platform.dto.TokenResponse
import com.cadence.performance_platform.dto.UserSessionResponse
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
    }fun refreshTokens(request: RefreshTokenRequest): TokenResponse {
        // For now, let's validate that a token was actually passed
        if (request.refreshToken.isBlank()) {
            throw IllegalArgumentException("Refresh token cannot be blank")
        }

        // Returning a mock response just like login to verify the pipeline works
        return TokenResponse(
            userId = java.util.UUID.randomUUID(), // Mock or extracted from original token
            email = "yadah@cadence.com",
            displayName = "Yadah Mudimeli",
            accessToken = "new-mock-access-token-from-refresh",
            status = "ACTIVE"
        )
    }fun getCurrentUserSession(): UserSessionResponse {
        // For now, returning mock data to verify your Postman routing works perfectly.
        // Later, you will extract the userId/email from the active security context/JWT token.
        return UserSessionResponse(
            userId = UUID.fromString("00000000-0000-0000-0000-000000000000"), // Placeholder UUID
            email = "yadah@cadence.com",
            displayName = "Yadah Mudimeli",
            role = "DEVELOPER",
            status = "ACTIVE"
        )
    }
    fun logout(request: LogoutRequest): MessageResponse {
        // Logic for blacklisting/invalidating tokens will go here
        return MessageResponse("Successfully logged out.")
    }

    fun processForgotPassword(request: ForgotPasswordRequest): MessageResponse {
        // Logic for generating temporary token & calling email service goes here
        return MessageResponse("If an account exists with ${request.email}, a reset link has been sent.")
    }

    fun processResetPassword(request: ResetPasswordRequest): MessageResponse {
        // Logic for verifying token validity and saving new password hash goes here
        return MessageResponse("Password has been reset successfully.")
    }
}