package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class FeedbackService {

    fun listFeedbackRequests(page: Int, size: Int): PagedFeedbackRequestResponse {
        val mockSummary = FeedbackRequestSummaryResponse(
            requestId = UUID.randomUUID(),
            targetUserName = "John Doe",
            reviewerName = "Jane Smith",
            status = "PENDING",
            deadline = OffsetDateTime.now().plusDays(14)
        )
        return PagedFeedbackRequestResponse(listOf(mockSummary), 1, 1, page, size)
    }

    fun requestFeedback(request: FeedbackRequestCreateRequest): BulkOperationResponse {
        return BulkOperationResponse(
            operationId = UUID.randomUUID(),
            status = "ACCEPTED",
            affectedRecordsCount = request.reviewerIds.size,
            message = "360 feedback requests generated successfully for processing."
        )
    }

    fun getQuestionnaire(requestId: UUID): FeedbackRequestDetailResponse {
        val mockQuestion = FeedbackQuestionResponse(
            questionId = UUID.randomUUID(),
            type = "SCALE",
            prompt = "How effectively does this individual collaborate across cross-functional engineering teams?",
            section = "COLLABORATION",
            isRequired = true
        )
        return FeedbackRequestDetailResponse(
            requestId = requestId,
            targetUserId = UUID.randomUUID(),
            requestorId = UUID.randomUUID(),
            templateId = UUID.randomUUID(),
            templateName = "Q3 Engineering Core Competencies",
            status = "PENDING",
            deadline = OffsetDateTime.now().plusDays(7),
            questions = listOf(mockQuestion)
        )
    }

    fun submitFeedback(requestId: UUID, request: FeedbackSubmissionRequest): FeedbackSubmissionResponse {
        return FeedbackSubmissionResponse(
            submissionId = UUID.randomUUID(),
            requestId = requestId,
            status = "SUBMITTED",
            submittedAt = OffsetDateTime.now()
        )
    }

    fun getAggregatedFeedback(userId: UUID): FeedbackSummaryResponse {
        return FeedbackSummaryResponse(
            userId = userId,
            totalResponsesReceived = 4,
            sectionAverages = mapOf("COLLABORATION" to 4.5, "TECHNICAL_SKILLS" to 4.8),
            openEndedInsights = listOf(
                "Demonstrates exceptional proficiency with architectural layouts.",
                "Consistently drives tasks to completion ahead of cadence sprints."
            ),
            isAnonymityThresholdMet = true
        )
    }
}