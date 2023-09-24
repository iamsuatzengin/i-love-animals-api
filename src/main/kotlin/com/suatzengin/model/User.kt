package com.suatzengin.model

import org.jetbrains.exposed.sql.Table
import java.util.UUID

data class User(
    val id: UUID,
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val profileImageUrl: String,
)

object UserTable: Table() {
    val id = uuid("id").autoGenerate()
    val fullName = varchar("full_name", 200)
    val email = varchar("email", 200)
    val password = varchar("password", 200)
    val phoneNumber = varchar("phone_number", 20)
    val profileImageUrl = varchar("image_url", 200)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}