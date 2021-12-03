package com.github.sookhee.data.datasource

import com.github.sookhee.domain.entity.Banner

interface BannerDataSource {
    suspend fun getBannerList(): List<Banner>
}