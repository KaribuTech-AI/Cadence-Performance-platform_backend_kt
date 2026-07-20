package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.ReviewTemplateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/review-templates")
class ReviewTemplateController(private val templateService: ReviewTemplateService) {

    @GetMapping
    fun getAllTemplates(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedReviewTemplateResponse> {
        return ResponseEntity.ok(templateService.getReviewTemplates(page, size))
    }

    @PostMapping
    fun createTemplate(
        @RequestBody request: ReviewTemplateCreateRequest
    ): ResponseEntity<ReviewTemplateResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(templateService.createReviewTemplate(request))
    }

    @GetMapping("/{templateId}")
    fun getTemplateById(
        @PathVariable templateId: UUID
    ): ResponseEntity<ReviewTemplateResponse> {
        return ResponseEntity.ok(templateService.getTemplateById(templateId))
    }

    @DeleteMapping("/{templateId}")
    fun deleteTemplate(
        @PathVariable templateId: UUID
    ): ResponseEntity<Void> {
        templateService.deleteTemplate(templateId)
        return ResponseEntity.noContent().build()
    }
}
