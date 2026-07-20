package com.cadence.performance_platform.dto

import java.util.UUID

data class EmployeeProfileResponse(
    val employeeId: UUID,
    val currentManagerId: UUID?,
    val updateStatus: String
)
