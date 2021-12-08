package com.github.sookhee.data.datasource

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.github.sookhee.domain.entity.ChatLog
import com.github.sookhee.domain.entity.ChatPreview
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor() : ChatDataSource {
    override suspend fun getChatPreview(employeeId: String): List<ChatPreview> {
        val chatPreviewList = mutableListOf<ChatPreview>()
        val database = Firebase.database.getReference(COLLECTION_ROOM)
        database.get()
            .addOnSuccessListener {
                Log.i("민지", "getChatPreview: $chatPreviewList")

                return@addOnSuccessListener
            }.addOnFailureListener {
                Log.i("민지", "getChatPreview: $chatPreviewList")
            }

        return chatPreviewList
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getChatLog(roomId: String): List<ChatLog> {
        val chatList = mutableListOf<ChatLog>()

        val database = Firebase.database.getReference(COLLECTION_LOG)
        val result = database.orderByChild("room_id").equalTo(roomId)
            .get()
            .await()

        if (result.value != null) {
            (result.value as HashMap<String, HashMap<String, String>>).forEach { (key, log) ->
                chatList.add(
                    ChatLog(
                        id = key,
                        room_id = log.getOrDefault("room_id", ""),
                        sender_id = log.getOrDefault("sender_id", ""),
                        time = log.getOrDefault("time", ""),
                        message = log.getOrDefault("message", ""),
                        message_type = log.getOrDefault("message_type", "")
                    )
                )
            }

            // 정렬
            chatList.sortBy { it.time }
        }

        return chatList
    }

    suspend fun createChatRoom(): String {
        val database = Firebase.database.getReference(COLLECTION_ROOM)
            .push()
            .setValue(
                ChatPreview(
                    id = "",
                    product_id = "",
                    product_image = "",
                    seller_id = "",
                    seller_name = "",
                    seller_image = "",
                    seller_area = "",
                    buyer_id = "",
                    buyer_name = "",
                    buyer_image = "",
                    last_message = "",
                    last_time = ""
                )
            ).await()

        return ""
    }

    companion object {
        private const val COLLECTION_ROOM = "room"
        private const val COLLECTION_LOG = "log"
    }
}