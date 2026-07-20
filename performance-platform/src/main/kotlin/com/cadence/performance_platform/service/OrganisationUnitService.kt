package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrganisationUnitService {

    fun getUnitTree(organisationId: UUID): List<OrganisationUnitTreeResponse> {
        // Mocking a tree root (e.g., Engineering Division) with a child (e.g., Backend Team)
        val childTeam = OrganisationUnitTreeResponse(
            unitId = UUID.randomUUID(),
            name = "Backend Engineering",
            code = "ENG-BACK",
            type = "TEAM",
            status = "ACTIVE"
        )
        val rootDivision = OrganisationUnitTreeResponse(
            unitId = UUID.randomUUID(),
            name = "Technology Division",
            code = "TECH-DIV",
            type = "DIVISION",
            status = "ACTIVE",
            children = listOf(childTeam)
        )
        return listOf(rootDivision)
    }

    fun createUnit(organisationId: UUID, request: OrganisationUnitCreateRequest): OrganisationUnitResponse {
        return OrganisationUnitResponse(
            unitId = UUID.randomUUID(),
            organisationId = organisationId,
            parentUnitId = request.parentUnitId,
            name = request.name,
            code = request.code,
            type = request.type,
            status = "ACTIVE"
        )
    }

    fun updateUnit(unitId: UUID, request: OrganisationUnitUpdateRequest): OrganisationUnitResponse {
        return OrganisationUnitResponse(
            unitId = unitId,
            organisationId = UUID.randomUUID(),
            parentUnitId = request.parentUnitId,
            name = request.name ?: "Updated Unit Name",
            code = "UNIT-CODE",
            type = "DEPARTMENT",
            status = request.status ?: "ACTIVE"
        )
    }
}