package com.github.sookhee.domain

import com.github.sookhee.domain.entity.Product

interface ProductRepository {
    suspend fun getProductList(): List<Product>

    suspend fun registerProduct(product: Product)

    suspend fun getProductDetail(productId: String): Product

    suspend fun getProductListWithQuery(key: String, value: String): List<Product>

    suspend fun getLikeProductList(userId: String): List<Product>

    suspend fun getIsLikeProduct(userId: String, productId: String): String
}
