package com.suatzengin.data.dao.profile

import com.suatzengin.data.dao.Dao
import com.suatzengin.data.request.profile.UpdateProfileRequest
import com.suatzengin.data.response.ProfileResponse
import com.suatzengin.model.ProfileTable
import com.suatzengin.model.UserTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

interface ProfileDao : Dao<ProfileResponse> {

    override fun resultRow(row: ResultRow): ProfileResponse = ProfileResponse(
        profileId = row[ProfileTable.profileId],
        userId = row[ProfileTable.userId].toString(),
        fullName = row[UserTable.fullName],
        email = row[UserTable.email],
        phoneNumber = row[UserTable.phoneNumber],
        profileImageUrl = row[UserTable.profileImageUrl],
        bio = row[ProfileTable.bio],
        twitterLink = row[ProfileTable.twitterLink],
        instagramLink = row[ProfileTable.instagramLink]
    )

    suspend fun getUserProfile(userId: UUID): ProfileResponse

    suspend fun createUserProfile(userId: UUID)

    suspend fun updateUserProfile(userId: UUID, request: UpdateProfileRequest): Boolean
}