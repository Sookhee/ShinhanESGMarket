package com.github.sookhee.data.spec

data class ProductRequest(
    val area_name: String,
    val created_at: String,
    val category_id: String,
    val owner: String,
    val owner_id: String,
    val title: String,
    val price: Int,
    val status: Int,
    val updated_at: String,
    val content: String,
    val photo_list: List<String>
)