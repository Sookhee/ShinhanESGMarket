package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.Product

interface GetProductListUseCase {
    operator suspend fun invoke(): List<Product>
}