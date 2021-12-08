package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.ChatRepository
import com.github.sookhee.domain.entity.ChatLog
import javax.inject.Inject

class GetChatLogUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : GetChatLogUseCase {
    override suspend fun invoke(roomId: String): List<ChatLog> {
        return repository.getChatLog(roomId)
    }
}