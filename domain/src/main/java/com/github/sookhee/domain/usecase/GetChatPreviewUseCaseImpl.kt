package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.ChatRepository
import com.github.sookhee.domain.entity.ChatPreview
import javax.inject.Inject

class GetChatPreviewUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : GetChatPreviewUseCase {
    override suspend fun invoke(employeeId: String): List<ChatPreview> {
        return repository.getChatPreview(employeeId)
    }
}