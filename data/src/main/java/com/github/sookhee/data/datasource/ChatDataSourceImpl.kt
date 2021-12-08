package com.github.sookhee.data.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.github.sookhee.domain.entity.ChatLog
import com.github.sookhee.domain.entity.ChatPreview
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor() : ChatDataSource {
    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun getChatPreview(employeeId: String): List<ChatPreview> {
        val chatPreviewList = mutableListOf<ChatPreview>()
        val database = Firebase.database.getReference(COLLECTION_ROOM)

        val buyerList = database.orderByChild("buyer_id").equalTo(employeeId).get().await()
        val sellerList = database.orderByChild("seller_id").equalTo(employeeId).get().await()

        buyerList.value?.let {
            (it as HashMap<String, HashMap<String, String>>).forEach { (key, room) ->
                chatPreviewList.add(
                    ChatPreview(
                        id = key,
                        product_id = room["product_id"] ?: "",
                        product_image = room["product_image"] ?: "",
                        product_title = room["product_title"] ?: "",
                        product_price = room["product_price"] ?: "",
                        seller_id = room["seller_id"] ?: "",
                        seller_name = room["seller_name"] ?: "",
                        seller_image = room["seller_image"] ?: "",
                        seller_area = room["seller_area"] ?: "",
                        buyer_id = room["buyer_id"] ?: "",
                        buyer_name = room["buyer_name"] ?: "",
                        buyer_image = room["buyer_image"] ?: "",
                        last_message = room["last_message"] ?: "",
                        last_time = room["last_time"] ?: ""
                    )
                )
            }
        }

        sellerList.value?.let {
            (it as HashMap<String, HashMap<String, String>>).forEach { (key, room) ->
                chatPreviewList.add(
                    ChatPreview(
                        id = key,
                        product_id = room["product_id"] ?: "",
                        product_image = room["product_image"] ?: "",
                        product_title = room["product_title"] ?: "",
                        product_price = room["product_price"] ?: "",
                        seller_id = room["seller_id"] ?: "",
                        seller_name = room["seller_name"] ?: "",
                        seller_image = room["seller_image"] ?: "",
                        seller_area = room["seller_area"] ?: "",
                        buyer_id = room["buyer_id"] ?: "",
                        buyer_name = room["buyer_name"] ?: "",
                        buyer_image = room["buyer_image"] ?: "",
                        last_message = room["last_message"] ?: "",
                        last_time = room["last_time"] ?: ""
                    )
                )
            }
        }

        chatPreviewList.sortBy { it.last_time }

        return chatPreviewList.distinct()
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
                    last_time = "",
                    product_title = "",
                    product_price = ""
                )
            ).await()

        return ""
    }

    companion object {
        private const val COLLECTION_ROOM = "room"
        private const val COLLECTION_LOG = "log"
    }
}