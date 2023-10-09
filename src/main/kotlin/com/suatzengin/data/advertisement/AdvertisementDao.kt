package com.suatzengin.data.advertisement

import com.suatzengin.data.request.advertisement.AdvertisementRequest
import com.suatzengin.data.request.advertisement.UpdateAdRequest
import com.suatzengin.model.Advertisement
import java.util.UUID

interface AdvertisementDao {
    suspend fun getAdvertisement(): List<Advertisement>

    suspend fun getAdvertisementById(id: UUID): Advertisement

    suspend fun addAdvertisement(advertisementRequest: AdvertisementRequest, userId: String)

    suspend fun updateAdvertisement(id: UUID, updateAdRequest: UpdateAdRequest): Boolean

    suspend fun deleteAdvertisement(id: UUID): Boolean
}