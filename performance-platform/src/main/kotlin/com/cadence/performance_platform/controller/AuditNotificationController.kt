package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.AuditNotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/system")
class AuditNotificationController(private val auditService: AuditNotificationService) {

    @GetMapping("/audit-logs")
    fun fetchAuditTrail(
        @RequestParam(required = false) actionType: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PagedAuditLogResponse> {
        return ResponseEntity.ok(auditService.getAuditTrail(actionType, page, size))
    }

    @GetMapping("/users/{userId}/alerts")
    fun fetchUserAlerts(
        @PathVariable userId: UUID
    ): ResponseEntity<List<SystemAlertResponse>> {
        return ResponseEntity.ok(auditService.getUserAlerts(userId))
    }

    @PatchMapping("/alerts/{alertId}/read")
    fun markRead(
        @PathVariable alertId: UUID
    ): ResponseEntity<Void> {
        auditService.markAlertAsRead(alertId)
        return ResponseEntity.noContent().build()
    }
}
