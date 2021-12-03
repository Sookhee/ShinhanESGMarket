package com.github.sookhee.domain.usecase

import com.github.sookhee.domain.entity.Banner

interface GetBannerListUseCase {
    suspend operator fun invoke(): List<Banner>
}