package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class SystemConfigService {

    fun getActiveConfigurations(group: String?): List<ConfigurationSettingResponse> {
        return listOf(
            ConfigurationSettingResponse(
                configKey = "platform.scoring.default-scale",
                configValue = "5.0",
                description = "Maximum normalized scoring threshold allowed within appraisal metrics.",
                groupName = group ?: "CORE",
                updatedBy = UUID.randomUUID(),
                updatedAt = OffsetDateTime.now()
            )
        )
    }

    fun updateConfiguration(key: String, request: ConfigurationUpdateRequest, adminId: UUID): ConfigurationSettingResponse {
        return ConfigurationSettingResponse(
            configKey = key,
            configValue = request.configValue,
            description = "Updated runtime execution property setting.",
            groupName = "CORE",
            updatedBy = adminId,
            updatedAt = OffsetDateTime.now()
        )
    }

    fun getFeatureToggles(): List<FeatureToggleResponse> {
        return listOf(
            FeatureToggleResponse(
                toggleKey = "feature.calibration.manager-overrides",
                isEnabled = true,
                strategy = "GLOBAL",
                updatedAt = OffsetDateTime.now().minusDays(5)
            ),
            FeatureToggleResponse(
                toggleKey = "feature.peer-feedback.anonymous-posts",
                isEnabled = false,
                strategy = "GLOBAL",
                updatedAt = OffsetDateTime.now().minusDays(12)
            )
        )
    }

    fun updateFeatureToggle(key: String, request: FeatureToggleUpdateRequest): FeatureToggleResponse {
        return FeatureToggleResponse(
            toggleKey = key,
            isEnabled = request.isEnabled,
            strategy = "GLOBAL",
            updatedAt = OffsetDateTime.now()
        )
    }
}
