package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.Product

interface RegisterProductUseCase {
    operator suspend fun invoke(
        product: Product
    )
}
