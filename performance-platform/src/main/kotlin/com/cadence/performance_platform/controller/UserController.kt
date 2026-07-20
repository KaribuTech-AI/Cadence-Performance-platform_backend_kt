package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedUserResponse> {
        return ResponseEntity.ok(userService.getUsers(page, size))
    }

    @PostMapping
    fun createUser(
        @RequestBody request: UserCreateRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request))
    }

    @GetMapping("/{userId}")
    fun getUserById(
        @PathVariable userId: UUID
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUserById(userId))
    }

    @PatchMapping("/{userId}")
    fun updateUser(
        @PathVariable userId: UUID,
        @RequestBody request: UserUpdateRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.updateUser(userId, request))
    }

    @PatchMapping("/{userId}/status")
    fun updateUserStatus(
        @PathVariable userId: UUID,
        @RequestBody request: UserStatusRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.updateUserStatus(userId, request))
    }

    @PutMapping("/{userId}/manager")
    fun assignManager(
        @PathVariable userId: UUID,
        @RequestBody request: ManagerAssignmentRequest
    ): ResponseEntity<EmployeeProfileResponse> {
        return ResponseEntity.ok(userService.assignManager(userId, request))
    }
}