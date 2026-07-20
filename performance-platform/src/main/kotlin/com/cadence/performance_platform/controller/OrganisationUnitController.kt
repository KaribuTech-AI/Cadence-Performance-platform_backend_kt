package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.OrganisationUnitService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class OrganisationUnitController(private val unitService: OrganisationUnitService) {

    @GetMapping("/organisations/{organisationId}/units")
    fun getOrganisationUnitTree(
        @PathVariable organisationId: UUID
    ): ResponseEntity<List<OrganisationUnitTreeResponse>> {
        return ResponseEntity.ok(unitService.getUnitTree(organisationId))
    }

    @PostMapping("/organisations/{organisationId}/units")
    fun createOrganisationUnit(
        @PathVariable organisationId: UUID,
        @RequestBody request: OrganisationUnitCreateRequest
    ): ResponseEntity<OrganisationUnitResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(unitService.createUnit(organisationId, request))
    }

    @PatchMapping("/units/{unitId}")
    fun updateOrganisationUnit(
        @PathVariable unitId: UUID,
        @RequestBody request: OrganisationUnitUpdateRequest
    ): ResponseEntity<OrganisationUnitResponse> {
        return ResponseEntity.ok(unitService.updateUnit(unitId, request))
    }
}