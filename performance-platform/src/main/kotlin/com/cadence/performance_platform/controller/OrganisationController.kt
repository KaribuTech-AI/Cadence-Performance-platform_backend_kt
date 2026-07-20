package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.OrganisationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/organisations")
class OrganisationController(private val organisationService: OrganisationService) {

    @GetMapping
    fun getAllOrganisations(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedOrganisationResponse> {
        return ResponseEntity.ok(organisationService.getOrganisations(page, size))
    }

    @PostMapping
    fun createOrganisation(
        @RequestBody request: OrganisationCreateRequest
    ): ResponseEntity<OrganisationResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(organisationService.createOrganisation(request))
    }

    @GetMapping("/{organisationId}")
    fun getOrganisationById(
        @PathVariable organisationId: UUID
    ): ResponseEntity<OrganisationResponse> {
        return ResponseEntity.ok(organisationService.getOrganisationById(organisationId))
    }

    @PatchMapping("/{organisationId}")
    fun updateOrganisation(
        @PathVariable organisationId: UUID,
        @RequestBody request: OrganisationUpdateRequest
    ): ResponseEntity<OrganisationResponse> {
        return ResponseEntity.ok(organisationService.updateOrganisation(organisationId, request))
    }
}