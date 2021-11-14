package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.ProductRepository
import com.github.sookhee.domain.entity.Product
import javax.inject.Inject

class GetProductListUseCaseImpl @Inject constructor(private val repository: ProductRepository): GetProductListUseCase {
    override suspend fun invoke(): List<Product> {
        return repository.getProductList()
    }
}
