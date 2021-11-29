package com.github.sookhee.domain.usecase

interface IsLikeProductUseCase {
    suspend operator fun invoke(userId: String, productId: String): Boolean
}