package com.github.sookhee.domain

import com.github.sookhee.domain.entity.Product

interface ProductRepository {
    suspend fun getProductList(): List<Product>
}
