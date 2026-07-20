package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.CompetencyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/competencies")
class CompetencyController(private val competencyService: CompetencyService) {

    @GetMapping
    fun getAllCompetencies(
        @RequestParam(required = false) category: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedCompetencyResponse> {
        return ResponseEntity.ok(competencyService.getCompetencies(category, page, size))
    }

    @PostMapping
    fun createCompetency(
        @RequestBody request: CompetencyCreateRequest
    ): ResponseEntity<CompetencyResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(competencyService.createCompetency(request))
    }

    @GetMapping("/{competencyId}")
    fun getCompetencyById(
        @PathVariable competencyId: UUID
    ): ResponseEntity<CompetencyResponse> {
        return ResponseEntity.ok(competencyService.getCompetencyById(competencyId))
    }

    @DeleteMapping("/{competencyId}")
    fun deleteCompetency(
        @PathVariable competencyId: UUID
    ): ResponseEntity<Void> {
        competencyService.deleteCompetency(competencyId)
        return ResponseEntity.noContent().build()
    }
}
