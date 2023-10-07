package com.suatzengin.model

import com.suatzengin.data.response.AdvertisementResponse
import com.suatzengin.data.response.Location
import com.suatzengin.util.columntype.array
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.TextColumnType
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

data class Advertisement(
    val id: UUID,
    val creatorId: UUID,
    val title: String,
    val description: String,
    val category: Int,
    val images: Array<String>,
    val longitude: String,
    val latitude: String,
    val isCompleted: Boolean,
    val createdAt: LocalDateTime
) {
    fun toResponseModel() = AdvertisementResponse(
        id = id.toString(),
        creatorId = creatorId.toString(),
        title = title,
        description = description,
        category = category,
        images = images,
        location = Location(
            longitude = longitude,
            latitude = latitude
        ),
        isCompleted = isCompleted,
        createdAt = createdAt.toString()
    )

}

object AdvertisementTable : Table() {
    val id = uuid(name = "id").autoGenerate()
    val creatorId = reference(
        name = "creator_id", refColumn = UserTable.id,
        onDelete = ReferenceOption.CASCADE, fkName = "fk_creator_id"
    )
    val title = varchar(name = "title", length = 200)
    val description = text("description")
    val category = integer("category")
    val images = array<String>("images", TextColumnType())
    val longitude = text("longitude")
    val latitude = text("latitude")
    val isCompleted = bool("is_completed")
    val createdAt = datetime("created_at")
}

