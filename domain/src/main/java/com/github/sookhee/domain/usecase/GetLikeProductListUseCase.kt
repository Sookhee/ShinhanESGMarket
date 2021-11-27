package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.Product

interface GetLikeProductListUseCase {
    operator suspend fun invoke(userId: String): List<Product>
}