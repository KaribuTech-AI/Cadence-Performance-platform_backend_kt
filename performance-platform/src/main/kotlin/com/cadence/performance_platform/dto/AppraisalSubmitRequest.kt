package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AppraisalSubmitRequest(
    @JsonProperty("answers") val answers: List<AppraisalSectionSubmission> = emptyList()
)
