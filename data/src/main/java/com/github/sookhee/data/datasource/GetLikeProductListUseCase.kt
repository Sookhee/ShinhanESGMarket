package com.github.sookhee.data.datasource

import com.github.sookhee.domain.entity.Product

interface GetLikeProductListUseCase {
    operator suspend fun invoke(userId: String): List<Product>
}