package com.suatzengin.data.dao.charityscore

import com.suatzengin.data.request.charityscore.UpdateScoreRequest
import com.suatzengin.data.response.CharityScoreResponse
import com.suatzengin.model.CharityScoreTable
import com.suatzengin.model.UserTable
import com.suatzengin.util.extensions.dbQuery
import org.jetbrains.exposed.sql.*
import java.util.*

class CharityScoreDaoImpl : CharityScoreDao {

    override suspend fun getAllCharityScore(): List<CharityScoreResponse> = dbQuery {
        CharityScoreTable
            .join(
                otherTable = UserTable,
                joinType = JoinType.INNER,
                onColumn = CharityScoreTable.userId,
                otherColumn = UserTable.id
            )
            .selectAll()
            .orderBy(CharityScoreTable.point, SortOrder.DESC)
            .map { row -> resultRow(row = row) }
            .toList()
    }

    override suspend fun addUserCharityScoreTable(userId: UUID) = dbQuery {
        val insertStatement = CharityScoreTable.insert { statement ->
            statement[this.userId] = userId
            statement[point] = 0
        }
    }

    override suspend fun updateUserCharityScore(request: UpdateScoreRequest): Boolean = dbQuery {
        CharityScoreTable.update({ CharityScoreTable.userId eq request.userUUID }) { updateStatement ->
            updateStatement.update(point) {
                point + request.point
            }
        } > 0
    }
}