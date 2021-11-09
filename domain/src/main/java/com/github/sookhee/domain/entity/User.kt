package com.github.sookhee.domain.entity

data class User(
    val employeeNo: Int,
    val nickname: String = "",
    val name: String = "",
    val userId: String = "",
    val userPw: String = "",
    val branchNo: String = "",
)
