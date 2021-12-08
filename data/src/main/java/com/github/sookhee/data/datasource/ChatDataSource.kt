package com.github.sookhee.data.datasource

import com.github.sookhee.domain.entity.ChatLog
import com.github.sookhee.domain.entity.ChatPreview

interface ChatDataSource {
    suspend fun getChatPreview(employeeId: String): List<ChatPreview>

    suspend fun getChatLog(roomId: String): List<ChatLog>
}