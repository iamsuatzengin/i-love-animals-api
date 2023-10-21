package com.suatzengin.data.request.profile

data class UpdateProfileRequest(
    val bio: String? = null,
    val twitterLink: String? = null,
    val instagramLink: String? = null
)
