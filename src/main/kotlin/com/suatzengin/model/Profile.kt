package com.suatzengin.model

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import java.util.UUID

data class Profile(
    val profileId: Int,
    val userId: UUID,
    val bio: String? = null,
    val twitterLink: String? = null,
    val instagramLink: String? = null
)

object ProfileTable : Table() {
    val profileId = integer(name = "profile_id").autoIncrement()
    val userId = reference(
        name = "user_id",
        refColumn = UserTable.id,
        fkName = "fk_user_id",
        onDelete = ReferenceOption.CASCADE
    )
    val bio = text(name = "bio").nullable()
    val twitterLink = text(name = "twitter_link").nullable()
    val instagramLink = text(name = "instagram_link").nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(profileId)
}
