package com.cadence.performance_platform.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class FeedbackSubmissionRequest(
    @field:NotEmpty(message = "Answers cannot be empty")
    @JsonProperty("answers") val answers: List<AnswerInput>
)

data class AnswerInput(
    @field:NotNull(message = "Question ID is required")
    @JsonProperty("questionId") val questionId: UUID,

    @JsonProperty("ratingValue") val ratingValue: Int?, // For Likert-scale questions

    @JsonProperty("textValue") val textValue: String?   // For open-ended questions
)
