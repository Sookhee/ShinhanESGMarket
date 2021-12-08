package com.github.sookhee.domain.entity

data class ChatPreview(
    val id: String,

    val product_id: String,
    val product_image: String,

    val seller_id: String,
    val seller_name: String,
    val seller_image: String,
    val seller_area: String,

    val buyer_id: String,
    val buyer_name: String,
    val buyer_image: String,

    val last_message: String,
    val last_time: String,
)