package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.ProductRepository
import com.github.sookhee.domain.entity.Product
import javax.inject.Inject

class GetProductDetailUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
): GetProductDetailUseCase {
    override suspend fun invoke(productId: String): Product {
        return repository.getProductDetail(productId)
    }
}