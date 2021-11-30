package com.github.sookhee.data

import com.github.sookhee.data.datasource.UserDataSource
import com.github.sookhee.domain.UserRepository
import com.github.sookhee.domain.entity.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {
    override suspend fun getUserInfo(employeeNo: String): User {
        return userDataSource.getUserInfo(employeeNo)
    }
}