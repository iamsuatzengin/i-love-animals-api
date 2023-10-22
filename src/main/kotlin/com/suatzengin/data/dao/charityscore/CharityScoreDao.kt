package com.suatzengin.data.dao.charityscore

import com.suatzengin.data.dao.Dao
import com.suatzengin.data.request.charityscore.UpdateScoreRequest
import com.suatzengin.data.response.CharityScoreResponse
import com.suatzengin.model.CharityScoreTable
import com.suatzengin.model.UserTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

interface CharityScoreDao : Dao<CharityScoreResponse> {
    override fun resultRow(row: ResultRow): CharityScoreResponse = CharityScoreResponse(
        id = row[CharityScoreTable.id],
        userId = row[CharityScoreTable.userId].toString(),
        userMail = row[UserTable.email],
        userFullName = row[UserTable.fullName],
        userImageUrl = row[UserTable.profileImageUrl],
        point = row[CharityScoreTable.point]
    )

    suspend fun getAllCharityScore(): List<CharityScoreResponse>

    suspend fun addUserCharityScoreTable(userId: UUID)

    suspend fun updateUserCharityScore(request: UpdateScoreRequest): Boolean
}

