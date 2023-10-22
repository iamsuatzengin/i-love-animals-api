package com.suatzengin.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val profileId: Int,
    val userId: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val profileImageUrl: String,
    val charityScorePoint: Long = 0,
    val bio: String? = null,
    val twitterLink: String? = null,
    val instagramLink: String? = null,
)
