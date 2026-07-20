package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class JobGradeService {

    fun getAllJobGrades(): JobGradeListResponse {
        val mockGrade = JobGradeResponse(
            jobGradeId = UUID.randomUUID(),
            name = "Manager Level 1",
            code = "M1",
            level = 4
        )
        return JobGradeListResponse(content = listOf(mockGrade))
    }

    fun createJobGrade(request: JobGradeCreateRequest): JobGradeResponse {
        return JobGradeResponse(
            jobGradeId = UUID.randomUUID(),
            name = request.name,
            code = request.code,
            level = request.level
        )
    }
}
