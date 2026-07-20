package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class ReviewTemplateService {

    fun getReviewTemplates(page: Int, size: Int): PagedReviewTemplateResponse {
        val mockTemplate = ReviewTemplateResponse(
            templateId = UUID.randomUUID(),
            name = "Standard Engineering Evaluation Template",
            description = "Core performance blueprint for technical engineering tracks",
            sections = listOf(
                TemplateSectionResponse(UUID.randomUUID(), "Core Engineering Competencies", "COMPETENCY", 40.0),
                TemplateSectionResponse(UUID.randomUUID(), "OKR & Objective Delivery", "GOAL", 60.0)
            ),
            isPublished = true,
            createdAt = OffsetDateTime.now()
        )
        return PagedReviewTemplateResponse(
            content = listOf(mockTemplate),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun createReviewTemplate(request: ReviewTemplateCreateRequest): ReviewTemplateResponse {
        val elements = request.sections.map {
            TemplateSectionResponse(UUID.randomUUID(), it.title, it.type, it.weight)
        }
        return ReviewTemplateResponse(
            templateId = UUID.randomUUID(),
            name = request.name,
            description = request.description,
            sections = elements,
            isPublished = false,
            createdAt = OffsetDateTime.now()
        )
    }

    fun getTemplateById(templateId: UUID): ReviewTemplateResponse {
        return ReviewTemplateResponse(
            templateId = templateId,
            name = "Individual Target Template Schema",
            description = null,
            sections = emptyList(),
            isPublished = false,
            createdAt = OffsetDateTime.now()
        )
    }

    fun deleteTemplate(templateId: UUID) {
        // Mocking successful resource cleanup execution logic
    }


    }

