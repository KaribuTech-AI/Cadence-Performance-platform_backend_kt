package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.PositionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/positions")
class PositionController(private val positionService: PositionService) {

    @GetMapping
    fun getAllPositions(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedPositionResponse> {
        return ResponseEntity.ok(positionService.getPositions(page, size))
    }

    @PostMapping
    fun createPosition(
        @RequestBody request: PositionCreateRequest
    ): ResponseEntity<PositionResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(positionService.createPosition(request))
    }
}
