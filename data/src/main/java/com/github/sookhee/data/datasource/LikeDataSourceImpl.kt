package com.github.sookhee.data.datasource

import com.github.sookhee.domain.entity.Like
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LikeDataSourceImpl @Inject constructor(): LikeDataSource {
    override suspend fun getUserLike(userId: String): List<Like> {
        val likeList = mutableListOf<Like>()
        val resultList = FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .whereEqualTo("user_id", userId)
            .get().await()

        resultList.forEach { document ->
            val userId = document.getString(KEY_USER_ID) ?: ""
            val productId = document.getString(KEY_PRODUCT_ID) ?: ""

            likeList.add(
                Like(userId, productId)
            )
        }

        return likeList
    }

    companion object {
        private const val COLLECTION = "user_like"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_PRODUCT_ID = "product_id"
    }
}