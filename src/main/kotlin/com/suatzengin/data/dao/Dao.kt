package com.suatzengin.data.dao

import org.jetbrains.exposed.sql.ResultRow

interface Dao<T> {
    fun resultRow(row: ResultRow): T
}
