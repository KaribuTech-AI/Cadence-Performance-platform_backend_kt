package com.cadence.performance_platform.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.CheckinService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import java.util.UUID

@RestController
@RequestMapping("/api/v1/checkins")
class CheckinController(private val checkinService: CheckinService) {

    @GetMapping
    fun getAllCheckins(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedCheckinResponse> {
        return ResponseEntity.ok(checkinService.listCheckins(page, size))
    }

    @PostMapping
    fun createCheckin(
        @Valid @RequestBody request: CheckinCreateRequest
    ): ResponseEntity<CheckinResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkinService.scheduleCheckin(request))
    }

    @GetMapping("/{checkinId}")
    fun getDetails(
        @PathVariable checkinId: UUID
    ): ResponseEntity<CheckinDetailResponse> {
        return ResponseEntity.ok(checkinService.getCheckinDetails(checkinId))
    }

    @PatchMapping("/{checkinId}")
    fun modifyCheckin(
        @PathVariable checkinId: UUID,
        @Valid @RequestBody request: CheckinUpdateRequest
    ): ResponseEntity<CheckinResponse> {
        return ResponseEntity.ok(checkinService.updateCheckin(checkinId, request))
    }

    @PostMapping("/{checkinId}/action-items")
    fun createActionItem(
        @PathVariable checkinId: UUID,
        @Valid @RequestBody request: ActionItemCreateRequest
    ): ResponseEntity<ActionItemResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkinService.addActionItem(checkinId, request))
    }
}