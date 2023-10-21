package com.suatzengin.data.dao.charityscore

import com.suatzengin.data.request.charityscore.UpdateScoreRequest
import com.suatzengin.model.CharityScore
import com.suatzengin.model.CharityScoreTable
import com.suatzengin.util.extensions.dbQuery
import org.jetbrains.exposed.sql.*
import java.util.*

class CharityScoreDaoImpl : CharityScoreDao {

    override suspend fun getAllCharityScore(): List<CharityScore> = dbQuery {
        CharityScoreTable.selectAll()
            .orderBy(CharityScoreTable.point, SortOrder.DESC)
            .map { row -> resultRow(row = row) }
            .toList()
    }

    override suspend fun addUserCharityScoreTable(userId: String) {
        val insertStatement = CharityScoreTable.insert { statement ->
            statement[this.userId] = UUID.fromString(userId)
            statement[point] = 0
        }
    }

    override suspend fun updateUserCharityScore(request: UpdateScoreRequest): Boolean = dbQuery {
        CharityScoreTable.update({ CharityScoreTable.userId eq request.userId }) { updateStatement ->
            with(SqlExpressionBuilder) {
                update {
                    it.update(point, point + request.point)
                }
            }
        } > 0
    }
}