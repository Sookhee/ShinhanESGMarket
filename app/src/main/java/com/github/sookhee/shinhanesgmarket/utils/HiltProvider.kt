package com.github.sookhee.shinhanesgmarket.utils

import com.github.sookhee.data.*
import com.github.sookhee.data.datasource.*
import com.github.sookhee.domain.*
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
    fun provideProductDataSource(likeDataSource: LikeDataSource): ProductDataSource =
        ProductDataSourceImpl(likeDataSource)

    @Singleton
    @Provides
    fun provideProductRepository(
        dataSource: ProductDataSource,
        likeDataSource: LikeDataSource
    ): ProductRepository =
        ProductRepositoryImpl(dataSource, likeDataSource)

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

    @Singleton
    @Provides
    fun provideGetProductListWithQueryUseCase(repository: ProductRepository): GetProductListWithQueryUseCase =
        GetProductListWithQueryUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideGetLikeDataSource(): LikeDataSource = LikeDataSourceImpl()

    @Singleton
    @Provides
    fun provideGetLikeProductListUseCase(repository: ProductRepository): GetLikeProductListUseCase =
        GetLikeProductListUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideIsLikeProductUseCase(repository: ProductRepository): IsLikeProductUseCase =
        IsLikeProductUseCaseImpl(repository)

    // USER
    @Singleton
    @Provides
    fun provideUserDataSource(): UserDataSource =
        UserDataSourceImpl()

    @Singleton
    @Provides
    fun provideUserRepository(dataSource: UserDataSource): UserRepository =
        UserRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideGetUserInfoUseCase(repository: UserRepository): GetUserInfoUseCase =
        GetUserInfoUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideRegisterUserUseCase(repository: UserRepository): RegisterUserUseCase =
        RegisterUserUseCaseImpl(repository)

    // BANNER
    @Singleton
    @Provides
    fun provideBannerDataSource(): BannerDataSource = BannerDataSourceImpl()

    @Singleton
    @Provides
    fun provideBannerRepository(dataSource: BannerDataSource): BannerRepository =
        BannerRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideGetBannerRepository(repository: BannerRepository): GetBannerListUseCase =
        GetBannerListUseCaseImpl(repository)

    // CHAT
    @Singleton
    @Provides
    fun provideChatDataSource(): ChatDataSource = ChatDataSourceImpl()

    @Singleton
    @Provides
    fun provideChatRepository(dataSource: ChatDataSource): ChatRepository =
        ChatRepositoryImpl(dataSource)

    @Singleton
    @Provides
    fun provideGetChatPreviewUseCase(repository: ChatRepository): GetChatPreviewUseCase =
        GetChatPreviewUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideGetChatLogUseCase(repository: ChatRepository): GetChatLogUseCase =
        GetChatLogUseCaseImpl(repository)
}
