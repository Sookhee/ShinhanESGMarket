package com.github.sookhee.data.datasource

import com.github.sookhee.data.spec.ProductRequest
import com.github.sookhee.data.spec.ProductResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor() : ProductDataSource {
    override suspend fun getProductList(): List<ProductResponse> {
        val productList = mutableListOf<ProductResponse>()
        val resultList = FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .orderBy(KEY_UPDATED_AT)
            .get().await()

        for (document in resultList) {
            val id = document.id
            val title = document.getString(KEY_TITLE) ?: ""
            val owner = document.getString(KEY_OWNER) ?: ""
            val price = document.getLong(KEY_PRICE) ?: 0
            val category = document.getLong(KEY_CATEGORY) ?: 0
            val status = document.getLong(KEY_STATUS) ?: 0
            val createdAt = document.getString(KEY_CREATED_AT) ?: ""
            val updatedAt = document.getString(KEY_UPDATED_AT) ?: ""
            val area = document.getString(KEY_AREA) ?: ""

            productList.add(
                ProductResponse(
                    id = id,
                    title = title,
                    owner = owner,
                    price = price.toInt(),
                    category = category.toInt(),
                    status = status.toInt(),
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                    area = area,
                )
            )
        }

        return productList
    }

    override fun registerProduct(product: ProductRequest) {
        FirebaseFirestore.getInstance()
            .collection("product")
            .add(product)
    }

    override suspend fun getProductDetail(productId: String): ProductResponse {
        val resultList = FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .document(productId)
            .get().await()

        return ProductResponse(
            id = resultList.id,
            title = resultList.getString(KEY_TITLE) ?: "",
            owner = resultList.getString(KEY_OWNER) ?: "",
            price = resultList.getLong(KEY_PRICE)?.toInt() ?: 0,
            category = resultList.getLong(KEY_CATEGORY)?.toInt() ?: 0,
            status = resultList.getLong(KEY_STATUS)?.toInt() ?: 0,
            createdAt = resultList.getString(KEY_CREATED_AT) ?: "",
            updatedAt = resultList.getString(KEY_UPDATED_AT) ?: "",
            area = resultList.getString(KEY_AREA) ?: "",
            content = resultList.getString(KEY_CONTENT) ?: ""
        )
    }

    companion object {
        private const val COLLECTION = "product"
        private const val KEY_TITLE = "feed_title"
        private const val KEY_OWNER = "feed_owner"
        private const val KEY_PRICE = "price"
        private const val KEY_CATEGORY = "feed_category_id"
        private const val KEY_STATUS = "status"
        private const val KEY_CREATED_AT = "created_at"
        private const val KEY_UPDATED_AT = "updated_at"
        private const val KEY_AREA = "area"
        private const val KEY_CONTENT = "content"
    }
}