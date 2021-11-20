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
            content = product.content,
            feed_category_id = product.category,
            feed_owner = product.owner,
            feed_title = product.title,
            price = product.price,
            status = product.status,
            updated_at = product.updatedAt
        )

        dataSource.registerProduct(productRequest)
    }

    override suspend fun getProductDetail(productId: String): Product {
        val result = dataSource.getProductDetail(productId)

        return Product(
            id = result.id,
            title = result.title,
            owner = result.owner,
            price = result.price,
            category = result.category,
            status = result.status,
            createdAt = result.createdAt,
            updatedAt = result.updatedAt,
            area = result.area,
            content = result.content
        )
    }

    override suspend fun getProductListWithQuery(key: String, value: String): List<Product> {
        val result = dataSource.getProductListWithQuery(key, value)
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
}