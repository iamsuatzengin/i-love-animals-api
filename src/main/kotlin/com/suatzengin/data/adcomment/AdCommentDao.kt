package com.suatzengin.data.adcomment

import com.suatzengin.data.request.adcomment.AdvertisementCommentRequest
import com.suatzengin.model.AdComment
import java.util.UUID

interface AdCommentDao {
    suspend fun getAdvertisementComments(advertisementId: UUID): List<AdComment>

    suspend fun addAdvertisementComment(
        advertisementId: UUID,
        userId: UUID,
        commentRequest: AdvertisementCommentRequest
    )

    suspend fun updateAdvertisementComment(commentId: Long, commentRequest: AdvertisementCommentRequest): Boolean

    suspend fun deleteAdvertisementComment(commentId: Long): Boolean
}