package com.suatzengin.data.response

import java.util.*

data class ProfileResponse(
    val profileId: Int,
    val userId: UUID,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val profileImageUrl: String,
    val charityScorePoint: Long = 0,
    val bio: String? = null,
    val twitterLink: String? = null,
    val instagramLink: String? = null,
)
