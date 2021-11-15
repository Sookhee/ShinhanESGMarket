package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.Product

interface RegisterProductUseCase {
    operator fun invoke(
        product: Product
    )
}
