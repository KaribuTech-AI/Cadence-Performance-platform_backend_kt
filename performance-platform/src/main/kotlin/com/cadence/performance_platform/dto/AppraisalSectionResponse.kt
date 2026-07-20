package com.cadence.performance_platform.dto

import java.util.UUID

data class AppraisalSectionResponse(
    val sectionId: UUID,
    val title: String,
    val selfComment: String?,
    val managerComment: String?,
    val rating: Double?
)
