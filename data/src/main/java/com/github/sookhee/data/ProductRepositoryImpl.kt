package com.github.sookhee.data

import com.github.sookhee.data.datasource.ProductDataSource
import com.github.sookhee.data.spec.ProductRequest
import com.github.sookhee.domain.ProductRepository
import com.github.sookhee.domain.entity.Product
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val dataSource: ProductDataSource) :
    ProductRepository {
    override suspend fun getProductList(): List<Product> {
        val result = dataSource.getProductList()
        val productList = mutableListOf<Product>()

        result.forEach {
            productList.add(
                Product(
                    id = it.id,
                    title = it.title,
                    owner = it.owner,
                    price = it.price,
                    category = it.category,
                    status = it.status,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                    area = it.area
                )
            )
        }

        return productList
    }

    override fun registerProduct(product: Product) {
        val productRequest = ProductRequest(
            area = product.area,
            created_at = product.createdAt,
            feed_category_id = product.category,
            feed_owner = product.owner,
            feed_title = product.title,
            id = product.id,
            price = product.price,
            status = product.status,
            updated_at = product.updatedAt
        )

        dataSource.registerProduct(productRequest)
    }
}