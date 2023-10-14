package com.suatzengin.model

import com.suatzengin.data.response.AdCommentResponse
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import java.util.UUID

data class AdComment(
    val commentId: Long,
    val comment: String,
    val advertisementId: UUID,
    val userId: UUID?
) {
    fun toResponseModel() = AdCommentResponse(
        commentId = commentId,
        comment = comment,
        advertisementId = advertisementId.toString(),
        userId = userId.toString()
    )
}

object AdCommentTable : Table() {
    val commentId = long(name = "comment_id").autoIncrement()
    val comment = text(name = "comment")
    val advertisementId = reference(
        name = "advertisement_id",
        refColumn = AdvertisementTable.id,
        onDelete = ReferenceOption.CASCADE,
        fkName = "fk_advertisement_id"
    )
    val userId = reference(
        name = "user_id",
        refColumn = UserTable.id,
        onDelete = ReferenceOption.SET_NULL,
        fkName = "fk_user_id"
    ).nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(commentId)
}