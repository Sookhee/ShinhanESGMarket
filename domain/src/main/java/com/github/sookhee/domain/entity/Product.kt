package com.github.sookhee.domain.entity

data class Product(
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
