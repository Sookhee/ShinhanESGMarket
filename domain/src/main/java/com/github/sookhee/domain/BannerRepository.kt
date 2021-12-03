package com.github.sookhee.domain

import com.github.sookhee.domain.entity.Banner

interface BannerRepository {
    suspend fun getBannerList(): List<Banner>
}