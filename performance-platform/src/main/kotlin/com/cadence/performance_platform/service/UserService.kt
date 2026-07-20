package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class UserService {

    fun getUsers(page: Int, size: Int): PagedUserResponse {
        val mockUser = UserResponse(
            userId = UUID.randomUUID(),
            email = "dev.engine@cadence.io",
            firstName = "Sipho",
            lastName = "Nkosi",
            positionId = UUID.randomUUID(),
            unitId = UUID.randomUUID(),
            managerId = null,
            status = "ACTIVE",
            createdAt = OffsetDateTime.now()
        )
        return PagedUserResponse(
            content = listOf(mockUser),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun createUser(request: UserCreateRequest): UserResponse {
        return UserResponse(
            userId = UUID.randomUUID(),
            email = request.email,
            firstName = request.firstName,
            lastName = request.lastName,
            positionId = request.positionId,
            unitId = request.unitId,
            managerId = request.managerId,
            status = "ACTIVE",
            createdAt = OffsetDateTime.now()
        )
    }

    fun getUserById(userId: UUID): UserResponse {
        return UserResponse(
            userId = userId,
            email = "lookup.user@cadence.io",
            firstName = "Thabo",
            lastName = "Mokoena",
            positionId = UUID.randomUUID(),
            unitId = UUID.randomUUID(),
            managerId = UUID.randomUUID(),
            status = "ACTIVE",
            createdAt = OffsetDateTime.now()
        )
    }

    fun updateUser(userId: UUID, request: UserUpdateRequest): UserResponse {
        return UserResponse(
            userId = userId,
            email = "updated.profile@cadence.io",
            firstName = request.firstName ?: "Thabo",
            lastName = request.lastName ?: "Mokoena",
            positionId = request.positionId,
            unitId = request.unitId,
            managerId = UUID.randomUUID(),
            status = "ACTIVE",
            createdAt = OffsetDateTime.now()
        )
    }

    fun updateUserStatus(userId: UUID, request: UserStatusRequest): UserResponse {
        return UserResponse(
            userId = userId,
            email = "status.changed@cadence.io",
            firstName = "Thabo",
            lastName = "Mokoena",
            positionId = null,
            unitId = null,
            managerId = null,
            status = request.status,
            createdAt = OffsetDateTime.now()
        )
    }

    fun assignManager(userId: UUID, request: ManagerAssignmentRequest): EmployeeProfileResponse {
        return EmployeeProfileResponse(
            employeeId = userId,
            currentManagerId = request.managerId,
            updateStatus = "SUCCESS"
        )
    }
}