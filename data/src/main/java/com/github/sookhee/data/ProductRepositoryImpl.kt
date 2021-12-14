package com.github.sookhee.data

import com.github.sookhee.data.datasource.LikeDataSource
import com.github.sookhee.data.datasource.ProductDataSource
import com.github.sookhee.data.spec.ProductRequest
import com.github.sookhee.domain.ProductRepository
import com.github.sookhee.domain.entity.Product
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource,
    private val likeDataSource: LikeDataSource
) :
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
                    area = it.area,
                    photoList = listToHash(it.photoList)
                )
            )
        }

        return productList
    }

    override suspend fun registerProduct(product: Product) {
        val photoList = dataSource.uploadProductImage(product.photoList)

        val productRequest = ProductRequest(
            area_name = product.area,
            created_at = product.createdAt,
            content = product.content,
            category_id = product.category.toString(),
            owner = product.owner,
            owner_id = product.owner_id,
            title = product.title,
            price = product.price,
            status = product.status,
            updated_at = product.updatedAt,
            photo_list = photoList
        )

        dataSource.registerProduct(productRequest)
    }

    override suspend fun getProductDetail(productId: String): Product {
        val result = dataSource.getProductDetail(productId)

        return Product(
            id = result.id,
            title = result.title,
            owner = result.owner,
            owner_id = result.ownerId,
            price = result.price,
            category = result.category,
            status = result.status,
            createdAt = result.createdAt,
            updatedAt = result.updatedAt,
            area = result.area,
            content = result.content,
            photoList = listToHash(result.photoList)
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
                    area = it.area,
                    photoList = listToHash(it.photoList)
                )
            )
        }

        return productList
    }

    private fun listToHash(list: List<String>): HashMap<String, String> {
        val hashMap = hashMapOf<String, String>()
        for (i in 0 until list.size) {
            hashMap["$i"] = list[i]
        }

        return hashMap
    }

    override suspend fun getLikeProductList(userId: String): List<Product> {
        val result = dataSource.getLikeProductList(userId)
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
                    area = it.area,
                    photoList = listToHash(it.photoList)
                )
            )
        }

        return productList
    }

    override suspend fun getIsLikeProduct(userId: String, productId: String): String {
        return likeDataSource.getIsUserLikeProduct(userId, productId)
    }
}