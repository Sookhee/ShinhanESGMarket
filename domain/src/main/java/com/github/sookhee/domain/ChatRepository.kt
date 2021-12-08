package com.github.sookhee.domain

import com.github.sookhee.domain.entity.ChatLog
import com.github.sookhee.domain.entity.ChatPreview

interface ChatRepository {
    suspend fun getChatPreview(employeeId: String): List<ChatPreview>

    suspend fun getChatLog(roomId: String): List<ChatLog>
}