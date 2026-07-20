package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class OrganisationService {

    fun getOrganisations(page: Int, size: Int): PagedOrganisationResponse {
        val mockOrg = OrganisationResponse(
            organisationId = UUID.randomUUID(),
            name = "Cadence HQ",
            code = "CAD-001",
            status = "ACTIVE",
            createdAt = OffsetDateTime.now()
        )
        return PagedOrganisationResponse(
            content = listOf(mockOrg),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun createOrganisation(request: OrganisationCreateRequest): OrganisationResponse {
        return OrganisationResponse(
            organisationId = UUID.randomUUID(),
            name = request.name,
            code = request.code,
            status = "ACTIVE",
            createdAt = OffsetDateTime.now()
        )
    }

    fun getOrganisationById(organisationId: UUID): OrganisationResponse {
        return OrganisationResponse(
            organisationId = organisationId,
            name = "Cadence HQ Target",
            code = "CAD-TGT",
            status = "ACTIVE",
            createdAt = OffsetDateTime.now()
        )
    }

    fun updateOrganisation(organisationId: UUID, request: OrganisationUpdateRequest): OrganisationResponse {
        return OrganisationResponse(
            organisationId = organisationId,
            name = request.name ?: "Updated Cadence HQ",
            code = "CAD-UPD",
            status = request.status ?: "ACTIVE",
            createdAt = OffsetDateTime.now()
        )
    }
}