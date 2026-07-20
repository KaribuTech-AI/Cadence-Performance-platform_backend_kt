package com.cadence.performance_platform.service

import com.cadence.performance_platform.dto.*
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class FeedbackService {

    fun getUserFeedbackWall(userId: UUID, page: Int, size: Int): PagedFeedbackResponse {
        val mockFeedback = FeedbackResponse(
            feedbackId = UUID.randomUUID(),
            senderId = UUID.randomUUID(),
            receiverId = userId,
            message = "Incredible work decoupling the entity layers on the new Spring components! Saved us massive refactoring debt.",
            visibility = "PUBLIC",
            badgeType = "INNOVATION",
            createdAt = OffsetDateTime.now()
        )
        return PagedFeedbackResponse(
            content = listOf(mockFeedback),
            totalElements = 1,
            totalPages = 1,
            pageNumber = page,
            pageSize = size
        )
    }

    fun sendFeedback(senderId: UUID, request: FeedbackCreateRequest): FeedbackResponse {
        return FeedbackResponse(
            feedbackId = UUID.randomUUID(),
            senderId = senderId,
            receiverId = request.receiverId,
            message = request.message,
            visibility = request.visibility,
            badgeType = request.badgeType,
            createdAt = OffsetDateTime.now()
        )
    }

    fun deleteFeedback(feedbackId: UUID) {
        // Mock cleanup execution sequence for modern platform content curation
    }
}

