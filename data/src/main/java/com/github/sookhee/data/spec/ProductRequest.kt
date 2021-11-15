package com.github.sookhee.data.spec

data class ProductRequest(
    val area: String,
    val created_at: String,
    val feed_category_id: Int,
    val feed_owner: String,
    val feed_title: String,
    val id: Int,
    val price: Int,
    val status: Int,
    val updated_at: String,
)