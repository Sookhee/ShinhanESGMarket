package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.ProductRepository
import com.github.sookhee.domain.entity.Product
import javax.inject.Inject

class RegisterProductUseCaseImpl @Inject constructor(private val repository: ProductRepository) :
    RegisterProductUseCase {
    override suspend fun invoke(product: Product) {
        repository.registerProduct(product)
    }
}
