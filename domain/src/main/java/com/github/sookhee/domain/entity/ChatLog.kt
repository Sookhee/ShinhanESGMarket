package com.github.sookhee.domain.entity

data class ChatLog(
    val id: String = "",
    val room_id: String = "",
    val sender_id: String = "",
    val time: String = "",
    val message: String = "",
    val message_type: String = "",
)