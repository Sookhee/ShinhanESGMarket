package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.ChatPreview

interface GetChatPreviewUseCase {
    suspend operator fun invoke(employeeId: String): List<ChatPreview>
}