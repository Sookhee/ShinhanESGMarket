package com.github.sookhee.domain.entity

data class User(
    var employee_no: String = "",
    var nickname: String = "",
    var name: String = "",
    var user_id: String = "",
    var user_pw: String = "",
    var branch_no: String = "",
    var branch_nm: String = "",
    var latitude: Long = 0L,
    var longitude: Long = 0L,
    var community_code: String = "",
    var community_name: String = "",
)
