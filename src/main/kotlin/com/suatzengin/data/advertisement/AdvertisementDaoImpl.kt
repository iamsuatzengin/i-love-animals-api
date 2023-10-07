package com.suatzengin.data.advertisement

import com.suatzengin.data.request.AdvertisementRequest
import com.suatzengin.model.Advertisement
import com.suatzengin.model.AdvertisementTable
import com.suatzengin.util.extensions.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.time.LocalDateTime
import java.util.UUID

class AdvertisementDaoImpl : AdvertisementDao {

    private fun resultRow(row: ResultRow) = Advertisement(
        id = row[AdvertisementTable.id],
        creatorId = row[AdvertisementTable.creatorId],
        title = row[AdvertisementTable.title],
        description = row[AdvertisementTable.description],
        category = row[AdvertisementTable.category],
        images = row[AdvertisementTable.images],
        longitude = row[AdvertisementTable.longitude],
        latitude = row[AdvertisementTable.latitude],
        isCompleted = row[AdvertisementTable.isCompleted],
        createdAt = row[AdvertisementTable.createdAt]
    )

    override suspend fun getAdvertisement(): List<Advertisement> = dbQuery {
        AdvertisementTable.selectAll()
            .orderBy(AdvertisementTable.createdAt, SortOrder.DESC)
            .map { row -> resultRow(row) }
            .toList()
    }

    override suspend fun addAdvertisement(advertisementRequest: AdvertisementRequest, userId: String) = dbQuery {
        val insertStatement = AdvertisementTable.insert { statement ->
            statement[creatorId] = UUID.fromString(userId)
            statement[title] = advertisementRequest.title
            statement[description] = advertisementRequest.description
            statement[category] = advertisementRequest.category
            statement[images] = advertisementRequest.images
            statement[longitude] = advertisementRequest.longitude
            statement[latitude] = advertisementRequest.latitude
            statement[isCompleted] = false
            statement[createdAt] = LocalDateTime.now()
        }
    }
}