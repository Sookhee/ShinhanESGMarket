package com.github.sookhee.data.datasource

import com.github.sookhee.domain.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
            user_id = user.id,
            user_pw = user.getString(KEY_USER_PW)?.trim() ?: "",
            branch_no = user.getString(KEY_BRANCH_NO)?.trim() ?: "",
            branch_nm = user.getString(KEY_BRANCH_NM)?.trim() ?: ""
        )
    }

    override suspend fun registerUser(user: User): Boolean {
        var firebaseUser: FirebaseUser? = null
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword("${user.employee_no}@doremi.com", user.user_pw)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUser = auth.currentUser
                }
            }
            .await()

        val updateResult = updateUserData(user)

        return firebaseUser != null && updateResult
    }

    private suspend fun updateUserData(user: User): Boolean {
        var isSuccess = false
        val db = FirebaseFirestore.getInstance()
        val mapUser = mutableMapOf<String, Any>()

        mapUser[KEY_USER_ID] = user.user_id
        mapUser[KEY_NICKNAME] = user.nickname

        db.collection(COLLECTION)
            .document(user.user_id)
            .update(mapUser)
            .addOnSuccessListener { isSuccess = true}
            .await()

        return isSuccess
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