package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.Product

interface RegisterProductUseCase {
    suspend operator fun invoke(
        product: Product
    ): Boolean
}
