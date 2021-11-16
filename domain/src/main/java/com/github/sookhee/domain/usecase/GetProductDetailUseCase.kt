package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.Product

interface GetProductDetailUseCase {
    operator suspend fun invoke(productId: String): Product
}