package com.github.sookhee.shinhanesgmarket.utils

import com.github.sookhee.data.CategoryRepositoryImpl
import com.github.sookhee.data.ProductRepositoryImpl
import com.github.sookhee.data.datasource.CategoryDataSource
import com.github.sookhee.data.datasource.CategoryDataSourceImpl
import com.github.sookhee.data.datasource.ProductDataSource
import com.github.sookhee.data.datasource.ProductDataSourceImpl
import com.github.sookhee.domain.CategoryRepository
import com.github.sookhee.domain.ProductRepository
import com.github.sookhee.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object HiltProvider {
    @Singleton
    @Provides
    fun provideCategoryDataSource(): CategoryDataSource =
        CategoryDataSourceImpl()

    @Singleton
    @Provides
    fun provideCategoryRepository(dataSource: CategoryDataSource): CategoryRepository =
        CategoryRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideGetCategoryListUseCase(repository: CategoryRepository): GetCategoryListUseCase =
        GetCategoryListUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideProductDataSource(): ProductDataSource =
        ProductDataSourceImpl()

    @Singleton
    @Provides
    fun provideProductRepository(dataSource: ProductDataSource): ProductRepository =
        ProductRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideGetProductListUseCase(repository: ProductRepository): GetProductListUseCase =
        GetProductListUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideRegisterProductUseCase(repository: ProductRepository): RegisterProductUseCase =
        RegisterProductUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideGetProductDetailUseCase(repository: ProductRepository): GetProductDetailUseCase =
        GetProductDetailUseCaseImpl(repository)
}
