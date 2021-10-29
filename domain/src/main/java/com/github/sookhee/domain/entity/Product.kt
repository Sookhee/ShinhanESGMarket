package com.github.sookhee.domain.entity

data class Product(
    val id: Int,
    val title: String = "",
    val area: String = "",
    val time: String = "",
    val price: Int = 0,
)