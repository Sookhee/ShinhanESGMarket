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
            val areaCommunityCode = document.getString(KEY_COMMUNITY_CODE) ?: ""
            val areaId = document.getString(KEY_AREA_ID) ?: ""
            val areaLatitude: Double = document.getDouble(KEY_AREA_LATITUDE) ?: 0.0
            val areaLongitude: Double = document.getDouble(KEY_AREA_LONGITUDE) ?: 0.0
            val areaName = document.getString(KEY_AREA_NAME) ?: ""
            val categoryId = document.getString(KEY_CATEGORY_ID) ?: ""
            val content = document.getString(KEY_CONTENT) ?: ""
            val createdAt = document.getString(KEY_CREATED_AT) ?: ""
            val ownerId = document.getString(KEY_OWNER_ID) ?: ""
            val ownerName = document.getString(KEY_OWNER_NAME) ?: ""
            val photoList = document.data.get(KEY_PHOTO_LIST) as? List<String> ?: emptyList()
            val price = document.getLong(KEY_PRICE) ?: 0
            val status = document.getLong(KEY_STATUS) ?: 0
            val title = document.getString(KEY_TITLE) ?: ""
            val updatedAt = document.getString(KEY_UPDATED_AT) ?: ""

            productList.add(
                ProductResponse(
                    area_community_code = areaCommunityCode,
                    area_id = areaId,
                    area_latitude = areaLatitude,
                    area_longitude = areaLongitude,
                    area_name = areaName,
                    category_id = categoryId,
                    content = content,
                    created_at = createdAt,
                    owner_id = ownerId,
                    owner_name = ownerName,
                    photoList = photoList,
                    price = price.toInt(),
                    status = status.toInt(),
                    title = title,
                    updated_at = updatedAt
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
            area_community_code = result.getString(KEY_TITLE) ?: "",
            area_id = result.getString(KEY_TITLE) ?: "",
            area_latitude = result.getDouble(KEY_TITLE) ?: 0.0,
            area_longitude = result.getDouble(KEY_TITLE) ?: 0.0,
            area_name = result.getString(KEY_TITLE) ?: "",
            category_id = result.getString(KEY_TITLE) ?: "",
            content = result.getString(KEY_TITLE) ?: "",
            created_at = result.getString(KEY_TITLE) ?: "",
            owner_id = result.getString(KEY_TITLE) ?: "",
            owner_name = result.getString(KEY_TITLE) ?: "",
            photoList = result.data?.get(KEY_PHOTO_LIST) as? List<String> ?: emptyList(),
            price = result.getLong(KEY_TITLE)?.toInt() ?: 0,
            status = result.getLong(KEY_TITLE)?.toInt() ?: 0,
            title = result.getString(KEY_TITLE) ?: "",
            updated_at = result.getString(KEY_TITLE) ?: ""
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
            val areaCommunityCode = document.getString(KEY_COMMUNITY_CODE) ?: ""
            val areaId = document.getString(KEY_AREA_ID) ?: ""
            val areaLatitude: Double = document.getDouble(KEY_AREA_LATITUDE) ?: 0.0
            val areaLongitude: Double = document.getDouble(KEY_AREA_LONGITUDE) ?: 0.0
            val areaName = document.getString(KEY_AREA_NAME) ?: ""
            val categoryId = document.getString(KEY_CATEGORY_ID) ?: ""
            val content = document.getString(KEY_CONTENT) ?: ""
            val createdAt = document.getString(KEY_CREATED_AT) ?: ""
            val ownerId = document.getString(KEY_OWNER_ID) ?: ""
            val ownerName = document.getString(KEY_OWNER_NAME) ?: ""
            val photoList = document.data.get(KEY_PHOTO_LIST) as? List<String> ?: emptyList()
            val price = document.getLong(KEY_PRICE) ?: 0
            val status = document.getLong(KEY_STATUS) ?: 0
            val title = document.getString(KEY_TITLE) ?: ""
            val updatedAt = document.getString(KEY_UPDATED_AT) ?: ""

            productList.add(
                ProductResponse(
                    area_community_code = areaCommunityCode,
                    area_id = areaId,
                    area_latitude = areaLatitude,
                    area_longitude = areaLongitude,
                    area_name = areaName,
                    category_id = categoryId,
                    content = content,
                    created_at = createdAt,
                    owner_id = ownerId,
                    owner_name = ownerName,
                    photoList = photoList,
                    price = price.toInt(),
                    status = status.toInt(),
                    title = title,
                    updated_at = updatedAt
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
                    area_community_code = document.getString(KEY_TITLE) ?: "",
                    area_id = document.getString(KEY_TITLE) ?: "",
                    area_latitude = document.getDouble(KEY_TITLE) ?: 0.0,
                    area_longitude = document.getDouble(KEY_TITLE) ?: 0.0,
                    area_name = document.getString(KEY_TITLE) ?: "",
                    category_id = document.getString(KEY_TITLE) ?: "",
                    content = document.getString(KEY_TITLE) ?: "",
                    created_at = document.getString(KEY_TITLE) ?: "",
                    owner_id = document.getString(KEY_TITLE) ?: "",
                    owner_name = document.getString(KEY_TITLE) ?: "",
                    photoList = document.data?.get(KEY_PHOTO_LIST) as? List<String> ?: emptyList(),
                    price = document.getLong(KEY_TITLE)?.toInt() ?: 0,
                    status = document.getLong(KEY_TITLE)?.toInt() ?: 0,
                    title = document.getString(KEY_TITLE) ?: "",
                    updated_at = document.getString(KEY_TITLE) ?: ""
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

        private const val KEY_COMMUNITY_CODE = "area_community_code"
        private const val KEY_AREA_ID = "area_id"
        private const val KEY_AREA_LATITUDE = "area_latitude"
        private const val KEY_AREA_LONGITUDE = "area_longitude"
        private const val KEY_AREA_NAME = "area_name"
        private const val KEY_CATEGORY_ID = "category_id"
        private const val KEY_CONTENT = "content"
        private const val KEY_CREATED_AT = "created_at"
        private const val KEY_OWNER_ID = "owner_id"
        private const val KEY_OWNER_NAME = "owner_name"
        private const val KEY_PHOTO_LIST = "photo_list"
        private const val KEY_PRICE = "price"
        private const val KEY_STATUS = "status"
        private const val KEY_TITLE = "title"
        private const val KEY_UPDATED_AT = "updated_at"

        private const val STORAGE_PATH = "/product_image"
    }
}