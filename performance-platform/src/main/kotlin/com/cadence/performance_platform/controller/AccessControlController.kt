package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.AccessControlService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class AccessControlController(private val rbacService: AccessControlService) {

    @GetMapping("/roles")
    fun getRoles(): ResponseEntity<RoleListResponse> {
        return ResponseEntity.ok(rbacService.getAllRoles())
    }

    @PostMapping("/roles")
    fun createRole(
        @RequestBody request: RoleCreateRequest
    ): ResponseEntity<RoleResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(rbacService.createRole(request))
    }

    @GetMapping("/permissions")
    fun getPermissions(): ResponseEntity<PermissionListResponse> {
        return ResponseEntity.ok(rbacService.getAllPermissions())
    }

    @PutMapping("/roles/{roleId}/permissions")
    fun updateRolePermissions(
        @PathVariable roleId: UUID,
        @RequestBody request: RolePermissionRequest
    ): ResponseEntity<RoleResponse> {
        return ResponseEntity.ok(rbacService.updateRolePermissions(roleId, request))
    }

    @PostMapping("/users/{userId}/roles")
    fun assignUserRole(
        @PathVariable userId: UUID,
        @RequestBody request: UserRoleAssignmentRequest
    ): ResponseEntity<UserRoleResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(rbacService.assignRoleToUser(userId, request))
    }

    @DeleteMapping("/users/{userId}/roles/{userRoleId}")
    fun deleteUserRole(
        @PathVariable userId: UUID,
        @PathVariable userRoleId: UUID
    ): ResponseEntity<Void> {
        rbacService.removeRoleAssignment(userId, userRoleId)
        return ResponseEntity.noContent().build()
    }
}

