package com.suatzengin.data.advertisement

import com.suatzengin.data.request.AdvertisementRequest
import com.suatzengin.model.Advertisement

interface AdvertisementDao {
    suspend fun getAdvertisement(): List<Advertisement>

    suspend fun addAdvertisement(advertisementRequest: AdvertisementRequest, userId: String)
}