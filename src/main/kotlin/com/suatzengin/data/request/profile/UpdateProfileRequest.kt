package com.suatzengin.data.request.profile

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val bio: String? = null,
    val twitterLink: String? = null,
    val instagramLink: String? = null
)
