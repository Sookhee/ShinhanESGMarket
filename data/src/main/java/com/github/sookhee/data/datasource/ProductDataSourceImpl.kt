package com.github.sookhee.data.datasource

import androidx.core.net.toUri
import com.github.sookhee.data.spec.ProductRequest
import com.github.sookhee.data.spec.ProductResponse
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(
    private val likeDataSource: LikeDataSource
) : ProductDataSource {
    override suspend fun getProductList(): List<ProductResponse> {
        val productList = mutableListOf<ProductResponse>()
        val resultList = FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .orderBy(KEY_UPDATED_AT, Query.Direction.DESCENDING)
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
            val photoList = document.data.get(KEY_PHOTO_LIST) as? List<String> ?: emptyList()

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
                    photoList = photoList
                )
            )
        }

        return productList
    }

    override suspend fun registerProduct(product: ProductRequest) {
        FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .add(product)
    }

    override suspend fun getProductDetail(productId: String): ProductResponse {
        val result = FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .document(productId)
            .get().await()

        return ProductResponse(
            id = result.id,
            title = result.getString(KEY_TITLE) ?: "",
            owner = result.getString(KEY_OWNER) ?: "",
            price = result.getLong(KEY_PRICE)?.toInt() ?: 0,
            category = result.getLong(KEY_CATEGORY)?.toInt() ?: 0,
            status = result.getLong(KEY_STATUS)?.toInt() ?: 0,
            createdAt = result.getString(KEY_CREATED_AT) ?: "",
            updatedAt = result.getString(KEY_UPDATED_AT) ?: "",
            area = result.getString(KEY_AREA) ?: "",
            content = result.getString(KEY_CONTENT) ?: "",
            photoList = result.data?.get(KEY_PHOTO_LIST) as? List<String> ?: emptyList()
        )
    }

    override suspend fun getProductListWithQuery(
        key: String,
        value: String
    ): List<ProductResponse> {
        val productList = mutableListOf<ProductResponse>()
        val resultList = FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .whereEqualTo(key, value)
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
            val photoList = document.data.get(KEY_PHOTO_LIST)

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
                    photoList = photoList as? List<String> ?: emptyList()
                )
            )
        }

        return productList
    }

    override suspend fun getLikeProductList(userId: String): List<ProductResponse> {
        val likeProductList = mutableListOf<ProductResponse>()

        val likeList = likeDataSource.getUserLike(userId)
        likeList.forEach {
            val document = FirebaseFirestore.getInstance()
                .collection(COLLECTION)
                .document(it.productId)
                .get().await()

            likeProductList.add(
                ProductResponse(
                    id = document.id,
                    title = document.getString(KEY_TITLE) ?: "",
                    owner = document.getString(KEY_OWNER) ?: "",
                    price = document.getLong(KEY_PRICE)?.toInt() ?: 0,
                    category = document.getLong(KEY_CATEGORY)?.toInt() ?: 0,
                    status = document.getLong(KEY_STATUS)?.toInt() ?: 0,
                    createdAt = document.getString(KEY_CREATED_AT) ?: "",
                    updatedAt = document.getString(KEY_UPDATED_AT) ?: "",
                    area = document.getString(KEY_AREA) ?: "",
                    content = document.getString(KEY_CONTENT) ?: "",
                    photoList = document.data?.get(KEY_PHOTO_LIST) as? List<String> ?: emptyList()
                )
            )
        }

        return likeProductList
    }

    override suspend fun uploadProductImage(photoList: HashMap<String, String>): List<String> {
        val storage = FirebaseStorage.getInstance().reference

        photoList.forEach { photo ->
            storage.child(STORAGE_PATH).child("/${photo.key}").putFile(photo.value.toUri())
                .addOnFailureListener { }
                .addOnSuccessListener { }
                .await()
        }

        val photoUriList = mutableListOf<String>()
        photoList.forEach { photo ->
            storage.child("/$STORAGE_PATH/${photo.key}").downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    photoUriList.add(task.result.toString())
                }
            }.await()
        }

        return photoUriList
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
        private const val KEY_PHOTO_LIST = "photo_list"

        private const val STORAGE_PATH = "/product_image"
    }
}