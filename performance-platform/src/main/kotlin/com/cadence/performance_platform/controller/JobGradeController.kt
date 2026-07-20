package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.JobGradeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/job-grades")
class JobGradeController(private val jobGradeService: JobGradeService) {

    @GetMapping
    fun getJobGrades(): ResponseEntity<JobGradeListResponse> {
        return ResponseEntity.ok(jobGradeService.getAllJobGrades())
    }

    @PostMapping
    fun createJobGrade(
        @RequestBody request: JobGradeCreateRequest
    ): ResponseEntity<JobGradeResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobGradeService.createJobGrade(request))
    }
}
