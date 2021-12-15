package com.github.sookhee.data.spec

data class ProductRequest(
    val area_community_code: String = "",
    val area_id: String = "",
    val area_latitude: Double = 0.0,
    val area_longitude: Double = 0.0,
    val area_name: String = "",
    val category_id: String = "",
    val content: String = "",
    val created_at: String = "",
    val owner_id: String = "",
    val owner_name: String = "",
    val photoList: List<String> = emptyList(),
    val price: Int = 0,
    val status: Int = 0,
    val title: String = "",
    val updated_at: String = ""
)