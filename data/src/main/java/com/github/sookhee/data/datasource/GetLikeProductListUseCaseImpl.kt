package com.github.sookhee.data.datasource

import com.github.sookhee.domain.ProductRepository
import com.github.sookhee.domain.entity.Product
import javax.inject.Inject

class GetLikeProductListUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
): GetLikeProductListUseCase {
    override suspend fun invoke(userId: String): List<Product> {
        return productRepository.getLikeProductList(userId)
    }
}