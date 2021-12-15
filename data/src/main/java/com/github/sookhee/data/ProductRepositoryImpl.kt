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
                    area_community_code = it.area_community_code,
                    area_id = it.area_id,
                    area_latitude = it.area_latitude,
                    area_longitude = it.area_longitude,
                    area_name = it.area_name,
                    category_id = it.category_id,
                    content = it.content,
                    created_at = it.created_at,
                    owner_id = it.owner_id,
                    owner_name = it.owner_name,
                    photoList = listToHash(it.photoList),
                    price = it.price,
                    status = it.status,
                    title = it.title,
                    updated_at = it.updated_at
                )
            )
        }

        return productList
    }

    override suspend fun registerProduct(product: Product): Boolean {
        val photoList = dataSource.uploadProductImage(product.photoList)

        val productRequest = ProductRequest(
            area_community_code = product.area_community_code,
            area_id = product.area_id,
            area_latitude = product.area_latitude,
            area_longitude = product.area_longitude,
            area_name = product.area_name,
            category_id = product.category_id,
            content = product.content,
            created_at = product.created_at,
            owner_id = product.owner_id,
            owner_name = product.owner_name,
            photo_list = photoList,
            price = product.price,
            status = product.status,
            title = product.title,
            updated_at = product.updated_at
        )

        return dataSource.registerProduct(productRequest)
    }

    override suspend fun getProductDetail(productId: String): Product {
        val result = dataSource.getProductDetail(productId)

        return Product(
            id = result.id,
            area_community_code = result.area_community_code,
            area_id = result.area_id,
            area_latitude = result.area_latitude,
            area_longitude = result.area_longitude,
            area_name = result.area_name,
            category_id = result.category_id,
            content = result.content,
            created_at = result.created_at,
            owner_id = result.owner_id,
            owner_name = result.owner_name,
            photoList = listToHash(result.photoList),
            price = result.price,
            status = result.status,
            title = result.title,
            updated_at = result.updated_at
        )
    }

    override suspend fun getProductListWithQuery(key: String, value: String): List<Product> {
        val result = dataSource.getProductListWithQuery(key, value)
        val productList = mutableListOf<Product>()

        result.forEach {
            productList.add(
                Product(
                    id = it.id,
                    area_community_code = it.area_community_code,
                    area_id = it.area_id,
                    area_latitude = it.area_latitude,
                    area_longitude = it.area_longitude,
                    area_name = it.area_name,
                    category_id = it.category_id,
                    content = it.content,
                    created_at = it.created_at,
                    owner_id = it.owner_id,
                    owner_name = it.owner_name,
                    photoList = listToHash(it.photoList),
                    price = it.price,
                    status = it.status,
                    title = it.title,
                    updated_at = it.updated_at
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
                    area_community_code = it.area_community_code,
                    area_id = it.area_id,
                    area_latitude = it.area_latitude,
                    area_longitude = it.area_longitude,
                    area_name = it.area_name,
                    category_id = it.category_id,
                    content = it.content,
                    created_at = it.created_at,
                    owner_id = it.owner_id,
                    owner_name = it.owner_name,
                    photoList = listToHash(it.photoList),
                    price = it.price,
                    status = it.status,
                    title = it.title,
                    updated_at = it.updated_at
                )
            )
        }

        return productList
    }

    override suspend fun getIsLikeProduct(userId: String, productId: String): String {
        return likeDataSource.getIsUserLikeProduct(userId, productId)
    }
}