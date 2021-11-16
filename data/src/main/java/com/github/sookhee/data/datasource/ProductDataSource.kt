package com.github.sookhee.data.datasource

import com.github.sookhee.data.spec.ProductRequest
import com.github.sookhee.data.spec.ProductResponse

interface ProductDataSource {
    suspend fun getProductList(): List<ProductResponse>

    fun registerProduct(product: ProductRequest)

    suspend fun getProductDetail(productId: String): ProductResponse
}