package com.suatzengin.data.dao.advertisement

import com.suatzengin.data.dao.Dao
import com.suatzengin.data.request.advertisement.AdvertisementRequest
import com.suatzengin.data.request.advertisement.UpdateAdRequest
import com.suatzengin.model.Advertisement
import java.util.UUID

interface AdvertisementDao : Dao<Advertisement> {
    suspend fun getAdvertisement(): List<Advertisement>

    suspend fun getAdvertisementById(id: UUID): Advertisement

    suspend fun getUserAdvertisement(userId: UUID): List<Advertisement>

    suspend fun searchAdvertisement(keyword: String): List<Advertisement>

    suspend fun getAdvertisementByCategory(category: Int): List<Advertisement>

    suspend fun addAdvertisement(advertisementRequest: AdvertisementRequest, userId: String)

    suspend fun updateAdvertisement(id: UUID, updateAdRequest: UpdateAdRequest): Boolean

    suspend fun deleteAdvertisement(id: UUID): Boolean
}