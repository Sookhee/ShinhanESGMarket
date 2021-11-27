package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.ProductRepository
import com.github.sookhee.domain.entity.Product

interface GetMyProductListUseCase {
    operator suspend fun invoke(key: String, value: String): List<Product>
}