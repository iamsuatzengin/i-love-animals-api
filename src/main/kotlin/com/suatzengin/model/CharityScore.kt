package com.suatzengin.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

@Serializable
data class CharityScore(
    val id: Int,
    val userId: String,
    val point: Long,
)

object CharityScoreTable : Table() {
    val id = integer(name = "id").autoIncrement()
    val userId = reference(
        name = "user_id",
        refColumn = UserTable.id,
        fkName = "fk_user_id",
        onDelete = ReferenceOption.CASCADE
    )
    val point = long(name = "point")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}