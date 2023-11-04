package com.suatzengin.data.dao.auth

import com.suatzengin.data.request.auth.RegisterRequest
import com.suatzengin.model.User
import com.suatzengin.model.UserTable
import com.suatzengin.util.extensions.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.UUID

class AuthDaoImpl : AuthDao {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[UserTable.id],
        fullName = row[UserTable.fullName],
        email = row[UserTable.email],
        password = row[UserTable.password],
        phoneNumber = row[UserTable.phoneNumber],
        profileImageUrl = row[UserTable.profileImageUrl]
    )

    override suspend fun register(registerRequest: RegisterRequest): UUID = dbQuery {
        val userEmail = findUserByEmail(email = registerRequest.email)

        if (userEmail != null) throw Exception("Böyle bir kullanıcı zaten var!")

        val insertStatement = UserTable.insert { insertStatement ->
            insertStatement[fullName] = registerRequest.fullName
            insertStatement[email] = registerRequest.email
            insertStatement[password] = registerRequest.password
            insertStatement[phoneNumber] = registerRequest.phoneNumber
            insertStatement[profileImageUrl] = registerRequest.profileImageUrl
        }

        insertStatement[UserTable.id]
    }

    override suspend fun findUserByEmail(email: String): User? = dbQuery {
        UserTable.select { UserTable.email eq email }
            .map { resultRow -> resultRowToUser(row = resultRow) }
            .singleOrNull()
    }
}
