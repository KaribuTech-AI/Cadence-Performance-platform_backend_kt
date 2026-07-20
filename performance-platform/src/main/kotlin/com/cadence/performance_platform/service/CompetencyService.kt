package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class CompetencyService {

    fun getCompetencies(category: String?, page: Int, size: Int): PagedCompetencyResponse {
        val mockCompetency = CompetencyResponse(
            competencyId = UUID.randomUUID(),
            name = "Architectural Thinking",
            description = "Ability to map system relationships and plan scalable software foundations.",
            category = category ?: "TECHNICAL",
            behaviors = listOf(
                BehaviorResponse(UUID.randomUUID(), "Designs modular services effectively", 4),
                BehaviorResponse(UUID.randomUUID(), "Identifies database scaling bottlenecks", 3)
            ),
            createdAt = OffsetDateTime.now()
        )
        return PagedCompetencyResponse(
            content = listOf(mockCompetency),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun createCompetency(request: CompetencyCreateRequest): CompetencyResponse {
        val indicators = request.behaviors.map {
            BehaviorResponse(UUID.randomUUID(), it.description, it.expectedLevel)
        }
        return CompetencyResponse(
            competencyId = UUID.randomUUID(),
            name = request.name,
            description = request.description,
            category = request.category,
            behaviors = indicators,
            createdAt = OffsetDateTime.now()
        )
    }

    fun getCompetencyById(competencyId: UUID): CompetencyResponse {
        return CompetencyResponse(
            competencyId = competencyId,
            name = "Collaborative Communication",
            description = "Syncs product needs seamlessly across engineering groups.",
            category = "CORE",
            behaviors = emptyList(),
            createdAt = OffsetDateTime.now()
        )
    }

    fun deleteCompetency(competencyId: UUID) {
        // Mock execution for data removal safety verification
    }
}
