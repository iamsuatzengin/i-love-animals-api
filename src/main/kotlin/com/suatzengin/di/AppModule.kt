package com.suatzengin.di

import com.suatzengin.data.auth.AuthDao
import com.suatzengin.data.auth.AuthDaoImpl
import org.koin.dsl.module

val appModule = module {
    single<AuthDao> { AuthDaoImpl() }
}
