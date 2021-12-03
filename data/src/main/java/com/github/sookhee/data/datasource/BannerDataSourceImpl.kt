package com.github.sookhee.data.datasource

import android.util.Log
import com.github.sookhee.domain.entity.Banner
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BannerDataSourceImpl @Inject constructor() : BannerDataSource {
    override suspend fun getBannerList(): List<Banner> {
        val bannerList = mutableListOf<Banner>()
        val result = FirebaseFirestore.getInstance()
            .collection(COLLECTION)
            .get()
            .await()

        result.forEach {
            bannerList.add(
                Banner(
                    url = it.getString(KEY_URL) ?: "",
                    image = it.getString(KEY_IMAGE) ?: ""
                )
            )
        }

        return bannerList
    }

    companion object {
        const val COLLECTION = "banner"

        const val KEY_URL = "url"
        const val KEY_IMAGE = "image"
    }
}