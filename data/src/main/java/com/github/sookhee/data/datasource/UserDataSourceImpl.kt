package com.github.sookhee.data.datasource

import com.github.sookhee.domain.entity.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor() : UserDataSource {
    override suspend fun getUserInfo(employeeNo: String): User {
        val db = FirebaseFirestore.getInstance()
        val result = db.collection(COLLECTION)
            .whereEqualTo(KEY_EMPLOYEE_NO, employeeNo)
            .get()
            .await()

        val user = result.documents[0]

        return User(
            employee_no = user.getString(KEY_EMPLOYEE_NO)?.trim() ?: "",
            nickname = user.getString(KEY_NICKNAME)?.trim() ?: "",
            name = user.getString(KEY_NAME)?.trim() ?: "",
            user_id = user.getString(KEY_USER_ID)?.trim() ?: "",
            user_pw = user.getString(KEY_USER_PW)?.trim() ?: "",
            branch_no = user.getString(KEY_BRANCH_NO)?.trim() ?: "",
            branch_nm = user.getString(KEY_BRANCH_NM)?.trim() ?: ""
        )
    }

    companion object {
        private const val COLLECTION = "user"

        private const val KEY_EMPLOYEE_NO = "employee_no"
        private const val KEY_BRANCH_NO = "branch_no"
        private const val KEY_BRANCH_NM = "branch_nm"
        private const val KEY_NAME = "name"
        private const val KEY_NICKNAME = "nickname"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_PW = "user_pw"
    }
}