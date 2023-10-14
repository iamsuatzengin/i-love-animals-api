package com.suatzengin.data.adcomment

import com.suatzengin.data.request.adcomment.AdvertisementCommentRequest
import com.suatzengin.model.AdComment
import com.suatzengin.model.AdCommentTable
import com.suatzengin.util.extensions.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class AdCommentDaoImpl : AdCommentDao {

    private fun resultRow(row: ResultRow) = AdComment(
        commentId = row[AdCommentTable.commentId],
        comment = row[AdCommentTable.comment],
        advertisementId = row[AdCommentTable.advertisementId],
        userId = row[AdCommentTable.userId]
    )

    override suspend fun getAdvertisementComments(advertisementId: UUID): List<AdComment> = dbQuery {
        AdCommentTable.select { AdCommentTable.advertisementId eq advertisementId }
            .orderBy(AdCommentTable.commentId, SortOrder.DESC)
            .map { row -> resultRow(row) }
            .toList()
    }

    override suspend fun addAdvertisementComment(
        advertisementId: UUID,
        userId: UUID,
        commentRequest: AdvertisementCommentRequest
    ) = dbQuery {
        val insertStatement = AdCommentTable.insert { statement ->
            statement[comment] = commentRequest.comment
            statement[this.advertisementId] = advertisementId
            statement[this.userId] = userId
        }
    }

    override suspend fun updateAdvertisementComment(
        commentId: Long,
        commentRequest: AdvertisementCommentRequest
    ): Boolean = dbQuery {
        AdCommentTable.update({ AdCommentTable.commentId eq commentId }) { updateStatement ->
            updateStatement[comment] = commentRequest.comment
        } > 0
    }

    override suspend fun deleteAdvertisementComment(commentId: Long): Boolean = dbQuery {
        AdCommentTable.deleteWhere { AdCommentTable.commentId eq commentId } > 0
    }
}