package com.cadence.performance_platform.controller


import com.cadence.performance_platform.dto.ForgotPasswordRequest
import com.cadence.performance_platform.dto.LoginRequest
import com.cadence.performance_platform.dto.LogoutRequest
import com.cadence.performance_platform.dto.MessageResponse
import com.cadence.performance_platform.dto.RefreshTokenRequest
import com.cadence.performance_platform.dto.ResetPasswordRequest
import com.cadence.performance_platform.dto.TokenResponse
import com.cadence.performance_platform.dto.UserSessionResponse
import com.cadence.performance_platform.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok(authService.authenticate(request))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequest): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok(authService.refreshTokens(request))
    }
    @GetMapping("/me")
    fun getCurrentUser(): ResponseEntity<UserSessionResponse> {
        return ResponseEntity.ok(authService.getCurrentUserSession())
    }
    @PostMapping("/logout")
    fun logout(@RequestBody request: LogoutRequest): ResponseEntity<MessageResponse> {
        return ResponseEntity.ok(authService.logout(request))
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(@RequestBody request: ForgotPasswordRequest): ResponseEntity<MessageResponse> {
        return ResponseEntity.ok(authService.processForgotPassword(request))
    }

    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody request: ResetPasswordRequest): ResponseEntity<MessageResponse> {
        return ResponseEntity.ok(authService.processResetPassword(request))
    }
}