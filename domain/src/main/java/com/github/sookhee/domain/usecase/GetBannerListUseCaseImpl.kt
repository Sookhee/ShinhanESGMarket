package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.BannerRepository
import javax.inject.Inject
import com.github.sookhee.domain.entity.Banner

class GetBannerListUseCaseImpl @Inject constructor(
    private val repository: BannerRepository
): GetBannerListUseCase {
    override suspend fun invoke(): List<Banner> {
        return repository.getBannerList()
    }
}