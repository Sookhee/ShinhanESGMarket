package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.ProductRepository
import javax.inject.Inject

class IsLikeProductUseCaseImpl @Inject constructor(private val repository: ProductRepository): IsLikeProductUseCase {
    override suspend fun invoke(userId: String, productId: String): String {
        return repository.getIsLikeProduct(userId, productId)
    }
}