package com.github.sookhee.domain.entity

data class ChatPreview(
    val traderName: String = "",
    val traderArea: String = "",
    val lastChatTime: String = "",
    val lastChatText: String = "",
    val traderProductId: Int = 0,
    val traderProductImage: String = "",
)