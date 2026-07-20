package com.cadence.performance_platform.controller

import com.cadence.performance_platform.dto.*
import com.cadence.performance_platform.service.SystemConfigService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/admin/config")
class SystemConfigController(private val configService: SystemConfigService) {

    @GetMapping("/settings")
    fun getSettings(@RequestParam(required = false) group: String?): ResponseEntity<List<ConfigurationSettingResponse>> {
        return ResponseEntity.ok(configService.getActiveConfigurations(group))
    }

    @PutMapping("/settings/{key}")
    fun updateSetting(
        @PathVariable key: String,
        @RequestHeader("X-Admin-Id") adminId: UUID,
        @RequestBody request: ConfigurationUpdateRequest
    ): ResponseEntity<ConfigurationSettingResponse> {
        return ResponseEntity.ok(configService.updateConfiguration(key, request, adminId))
    }

    @GetMapping("/features")
    fun getToggles(): ResponseEntity<List<FeatureToggleResponse>> {
        return ResponseEntity.ok(configService.getFeatureToggles())
    }

    @PatchMapping("/features/{key}")
    fun updateToggle(
        @PathVariable key: String,
        @RequestBody request: FeatureToggleUpdateRequest
    ): ResponseEntity<FeatureToggleResponse> {
        return ResponseEntity.ok(configService.updateFeatureToggle(key, request))
    }
}
