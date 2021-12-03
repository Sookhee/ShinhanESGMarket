package com.github.sookhee.data.datasource

import com.github.sookhee.domain.entity.Like
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LikeDataSourceImpl @Inject constructor() : LikeDataSource {
    override suspend fun getUserLike(userId: String): List<Like> {
        val likeList = mutableListOf<Like>()
        val resultList = FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .whereEqualTo(KEY_USER_ID, userId)
            .get().await()

        resultList.forEach { document ->
            val id = document.id
            val userId = document.getString(KEY_USER_ID) ?: ""
            val productId = document.getString(KEY_PRODUCT_ID) ?: ""

            likeList.add(
                Like(id, userId, productId)
            )
        }

        return likeList
    }

    override suspend fun getIsUserLikeProduct(userId: String, productId: String): String {
        val resultList = FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .whereEqualTo(KEY_USER_ID, userId)
            .whereEqualTo(KEY_PRODUCT_ID, productId)
            .get().await()

        return if (resultList.size() > 0) resultList.documents[0].id else ""
    }

    companion object {
        private const val COLLECTION = "user_like"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_PRODUCT_ID = "product_id"
    }
}