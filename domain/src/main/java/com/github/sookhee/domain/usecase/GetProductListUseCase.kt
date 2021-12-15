package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.Product

interface GetProductListUseCase {
    suspend operator fun invoke(): List<Product>
}