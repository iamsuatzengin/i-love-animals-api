package com.suatzengin.data.dao.charityscore

import com.suatzengin.data.dao.Dao
import com.suatzengin.data.request.charityscore.UpdateScoreRequest
import com.suatzengin.model.CharityScore
import com.suatzengin.model.CharityScoreTable
import org.jetbrains.exposed.sql.ResultRow

interface CharityScoreDao : Dao<CharityScore> {
    override fun resultRow(row: ResultRow): CharityScore = CharityScore(
        id = row[CharityScoreTable.id],
        userId = row[CharityScoreTable.userId],
        point = row[CharityScoreTable.point]
    )

    suspend fun getAllCharityScore(): List<CharityScore>

    suspend fun addUserCharityScoreTable(userId: String)

    suspend fun updateUserCharityScore(request: UpdateScoreRequest): Boolean
}

