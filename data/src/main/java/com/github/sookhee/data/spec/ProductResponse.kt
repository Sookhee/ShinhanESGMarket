package com.github.sookhee.data.spec

data class ProductResponse(
    val id: Int,
    val title: String = "",
    val owner: String = "",
    val price: Int = 0,
    val category: Int = 0,
    val status: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = "",
    val area: String = "",
)