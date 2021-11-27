package com.github.sookhee.data.datasource

import com.github.sookhee.data.spec.ProductRequest
import com.github.sookhee.data.spec.ProductResponse
import com.github.sookhee.domain.entity.Product

interface ProductDataSource {
    suspend fun getProductList(): List<ProductResponse>

    suspend fun getProductDetail(productId: String): ProductResponse

    suspend fun getProductListWithQuery(key: String, value: String): List<ProductResponse>

    suspend fun getLikeProductList(userId: String): List<Product>

    suspend fun registerProduct(product: ProductRequest)

    suspend fun uploadProductImage(photoList: HashMap<String, String>)
}