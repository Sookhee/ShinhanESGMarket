package com.github.sookhee.data

import com.github.sookhee.data.datasource.BannerDataSource
import com.github.sookhee.domain.BannerRepository
import com.github.sookhee.domain.entity.Banner
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val dataSource: BannerDataSource
) : BannerRepository {
    override suspend fun getBannerList(): List<Banner> {
        return dataSource.getBannerList()
    }
}