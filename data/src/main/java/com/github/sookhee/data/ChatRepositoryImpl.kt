package com.github.sookhee.data

import com.github.sookhee.data.datasource.ChatDataSource
import com.github.sookhee.domain.ChatRepository
import com.github.sookhee.domain.entity.ChatLog
import com.github.sookhee.domain.entity.ChatPreview
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val dataSource: ChatDataSource
) : ChatRepository {
    override suspend fun getChatPreview(employeeId: String): List<ChatPreview> {
        return dataSource.getChatPreview(employeeId)
    }

    override suspend fun getChatLog(roomId: String): List<ChatLog> {
        return dataSource.getChatLog(roomId)
    }
}