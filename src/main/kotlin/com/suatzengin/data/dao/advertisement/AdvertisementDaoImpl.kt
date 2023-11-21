package com.suatzengin.data.dao.advertisement

import com.suatzengin.data.request.advertisement.AdvertisementRequest
import com.suatzengin.data.request.advertisement.UpdateAdRequest
import com.suatzengin.model.Advertisement
import com.suatzengin.model.AdvertisementTable
import com.suatzengin.util.extensions.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime
import java.util.*

class AdvertisementDaoImpl : AdvertisementDao {

    override fun resultRow(row: ResultRow) = Advertisement(
        id = row[AdvertisementTable.id],
        creatorId = row[AdvertisementTable.creatorId],
        title = row[AdvertisementTable.title],
        description = row[AdvertisementTable.description],
        category = row[AdvertisementTable.category],
        images = row[AdvertisementTable.images],
        longitude = row[AdvertisementTable.longitude],
        latitude = row[AdvertisementTable.latitude],
        isCompleted = row[AdvertisementTable.isCompleted],
        createdAt = row[AdvertisementTable.createdAt],
        address = row[AdvertisementTable.address]
    )

    override suspend fun getAdvertisement(): List<Advertisement> = dbQuery {
        AdvertisementTable.selectAll()
            .orderBy(AdvertisementTable.createdAt, SortOrder.DESC)
            .map { row -> resultRow(row) }
            .toList()
    }

    override suspend fun getAdvertisementById(id: UUID): Advertisement = dbQuery {
        AdvertisementTable.select { AdvertisementTable.id eq id }
            .map(::resultRow)
            .single()
    }

    override suspend fun getUserAdvertisement(userId: UUID): List<Advertisement> = dbQuery {
        AdvertisementTable.select { AdvertisementTable.creatorId eq userId }
            .map(::resultRow)
            .toList()
    }

    override suspend fun searchAdvertisement(keyword: String) = dbQuery {
        AdvertisementTable.select {
            (AdvertisementTable.title.lowerCase() like "%${keyword.lowercase()}%").or(
                AdvertisementTable.description.lowerCase() like "%${keyword.lowercase()}%"
            )
        }
            .orderBy(AdvertisementTable.createdAt, SortOrder.DESC)
            .map { row -> resultRow(row) }
            .toList()
    }

    override suspend fun getAdvertisementByCategory(category: Int): List<Advertisement> = dbQuery {
        AdvertisementTable.select {
            AdvertisementTable.category eq category
        }.map (::resultRow).toList()
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
            statement[address] = advertisementRequest.address
            statement[isCompleted] = false
            statement[createdAt] = LocalDateTime.now()
        }
    }

    override suspend fun updateAdvertisement(id: UUID, updateAdRequest: UpdateAdRequest): Boolean = dbQuery {
        AdvertisementTable.update({ AdvertisementTable.id eq id }) { updateStatement ->
            updateStatement[title] = updateAdRequest.title
            updateStatement[description] = updateAdRequest.description
            updateStatement[category] = updateAdRequest.category
            updateStatement[address] = updateAdRequest.address
            updateStatement[isCompleted] = updateAdRequest.isCompleted
        } > 0
    }

    override suspend fun deleteAdvertisement(id: UUID): Boolean = dbQuery {
        AdvertisementTable.deleteWhere { AdvertisementTable.id eq id } > 0
    }
}