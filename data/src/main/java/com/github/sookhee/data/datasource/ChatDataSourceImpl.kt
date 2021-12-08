package com.github.sookhee.data.datasource

import android.util.Log
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

    override suspend fun getChatLog(roomId: String): List<ChatLog> {
        val database = Firebase.database.getReference(COLLECTION_LOG)
        val result = database.get().await()

        Log.i("민지", "datasource get chat log: $result")

        return emptyList()
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

        Log.i("민지", "")

        return ""
    }

    companion object {
        private const val COLLECTION_ROOM = "room"
        private const val COLLECTION_LOG = "log"
    }
}