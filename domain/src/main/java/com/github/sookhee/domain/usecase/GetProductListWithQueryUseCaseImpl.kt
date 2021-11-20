package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.ProductRepository
import com.github.sookhee.domain.entity.Product
import javax.inject.Inject

class GetProductListWithQueryUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
): GetProductListWithQueryUseCase {
    override suspend fun invoke(key: String, value: String): List<Product> {
        return repository.getProductListWithQuery(key, value)
    }
}