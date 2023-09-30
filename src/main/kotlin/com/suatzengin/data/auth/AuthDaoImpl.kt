package com.suatzengin.data.auth

import com.suatzengin.data.request.RegisterRequest
import com.suatzengin.model.User
import com.suatzengin.model.UserTable
import com.suatzengin.util.extensions.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class AuthDaoImpl: AuthDao {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[UserTable.id],
        fullName = row[UserTable.fullName],
        email = row[UserTable.email],
        password = row[UserTable.password],
        phoneNumber = row[UserTable.phoneNumber],
        profileImageUrl = row[UserTable.profileImageUrl]
    )

    override suspend fun register(registerRequest: RegisterRequest) = dbQuery {
        val userEmail = findUserByEmail(email = registerRequest.email)

        if(userEmail != null) throw Exception("This user already exist!")

        val insertStatement = UserTable.insert { insertStatement ->
            insertStatement[fullName] = registerRequest.fullName
            insertStatement[email] = registerRequest.email
            insertStatement[password] = registerRequest.password
            insertStatement[phoneNumber] = registerRequest.phoneNumber
            insertStatement[profileImageUrl] = registerRequest.profileImageUrl
        }
    }

    override suspend fun findUserByEmail(email: String): User? = dbQuery {
        UserTable.select { UserTable.email eq email }
            .map { resultRow -> resultRowToUser(row = resultRow) }
            .singleOrNull()
    }
}
