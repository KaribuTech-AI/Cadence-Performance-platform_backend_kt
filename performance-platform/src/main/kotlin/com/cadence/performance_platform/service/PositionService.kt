package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PositionService {

    fun getPositions(page: Int, size: Int): PagedPositionResponse {
        val mockPosition = PositionResponse(
            positionId = UUID.randomUUID(),
            title = "Senior Software Engineer",
            code = "SWE-SR",
            jobGradeId = UUID.randomUUID(),
            status = "ACTIVE"
        )
        return PagedPositionResponse(
            content = listOf(mockPosition),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun createPosition(request: PositionCreateRequest): PositionResponse {
        return PositionResponse(
            positionId = UUID.randomUUID(),
            title = request.title,
            code = request.code,
            jobGradeId = request.jobGradeId,
            status = "ACTIVE"
        )
    }
}