package com.suatzengin.data.dao.profile

import com.suatzengin.data.request.profile.UpdateProfileRequest
import com.suatzengin.data.response.ProfileResponse
import com.suatzengin.model.CharityScoreTable
import com.suatzengin.model.ProfileTable
import com.suatzengin.model.UserTable
import com.suatzengin.util.extensions.dbQuery
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import java.util.*

class ProfileDaoImpl : ProfileDao {

    override suspend fun getUserProfile(userId: UUID): ProfileResponse = dbQuery {
        ProfileTable.join(
            otherTable = UserTable,
            joinType = JoinType.INNER,
            onColumn = ProfileTable.userId,
            otherColumn = UserTable.id
        ).join(
            otherTable = CharityScoreTable,
            joinType = JoinType.INNER,
            onColumn = ProfileTable.userId,
            otherColumn = CharityScoreTable.userId
        )
            .select { ProfileTable.userId eq userId }
            .map(::resultRow)
            .single()
    }

    override suspend fun createUserProfile(userId: UUID) = dbQuery {
        val insertStatement = ProfileTable.insert { statement ->
            statement[this.userId] = userId
        }
    }

    override suspend fun updateUserProfile(userId: UUID, request: UpdateProfileRequest): Boolean = dbQuery {
        ProfileTable.update({ ProfileTable.userId eq userId }) { updateStatement ->
            updateStatement[bio] = request.bio
            updateStatement[twitterLink] = request.twitterLink
            updateStatement[instagramLink] = request.instagramLink
        } > 0
    }
}