package com.suatzengin.data.request.adcomment

import kotlinx.serialization.Serializable

@Serializable
data class AdvertisementCommentRequest(
    val comment: String
)