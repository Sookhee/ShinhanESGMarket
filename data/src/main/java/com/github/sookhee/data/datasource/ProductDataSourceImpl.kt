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
            val id = document.getLong(KEY_ID)?.toInt() ?: 0
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
                    area = area
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

    companion object {
        private const val COLLECTION = "product"
        private const val KEY_ID = "ID"
        private const val KEY_TITLE = "FEED_TITLE"
        private const val KEY_OWNER = "FEED_OWNER"
        private const val KEY_PRICE = "PRICE"
        private const val KEY_CATEGORY = "FEED_CATEGORY_ID"
        private const val KEY_STATUS = "STATUS"
        private const val KEY_CREATED_AT = "CREATED_AT"
        private const val KEY_UPDATED_AT = "UPDATED_AT"
        private const val KEY_AREA = "AREA"
    }
}