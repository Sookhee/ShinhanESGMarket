package com.github.sookhee.domain.entity

data class Banner(
    val title: String = "",
    val message: String = "",
    val imageUrl: String = "",
    val hookMessage: String = "",
    val emoji: String = "",
    val backgroundColor: String = ""
)