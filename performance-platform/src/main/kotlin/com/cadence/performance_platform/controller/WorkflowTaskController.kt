package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.WorkflowTaskService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class WorkflowTaskController(private val workflowTaskService: WorkflowTaskService) {

    @GetMapping("/tasks")
    fun getTasks(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedWorkflowTaskResponse> {
        return ResponseEntity.ok(workflowTaskService.listTasks(page, size))
    }

    @GetMapping("/tasks/{taskId}")
    fun getTaskDetails(@PathVariable taskId: UUID): ResponseEntity<WorkflowTaskDetailResponse> {
        return ResponseEntity.ok(workflowTaskService.getTaskDetails(taskId))
    }

    @PostMapping("/tasks/{taskId}/complete")
    fun completeTask(
        @PathVariable taskId: UUID,
        @Valid @RequestBody request: TaskCompletionRequest
    ): ResponseEntity<WorkflowTaskResponse> {
        return ResponseEntity.ok(workflowTaskService.completeTask(taskId, request))
    }

    @PostMapping("/tasks/{taskId}/reassign")
    fun reassignTask(
        @PathVariable taskId: UUID,
        @Valid @RequestBody request: TaskReassignmentRequest
    ): ResponseEntity<WorkflowTaskResponse> {
        return ResponseEntity.ok(workflowTaskService.reassignTask(taskId, request))
    }
}
