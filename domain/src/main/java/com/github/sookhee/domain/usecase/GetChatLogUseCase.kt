package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.ChatLog

interface GetChatLogUseCase {
    suspend operator fun invoke(roomId: String): List<ChatLog>
}