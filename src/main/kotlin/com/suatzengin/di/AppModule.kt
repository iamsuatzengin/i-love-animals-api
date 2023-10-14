package com.suatzengin.di

import com.suatzengin.data.adcomment.AdCommentDao
import com.suatzengin.data.adcomment.AdCommentDaoImpl
import com.suatzengin.data.advertisement.AdvertisementDao
import com.suatzengin.data.advertisement.AdvertisementDaoImpl
import com.suatzengin.data.auth.AuthDao
import com.suatzengin.data.auth.AuthDaoImpl
import org.koin.dsl.module

val appModule = module {
    single<AuthDao> { AuthDaoImpl() }
    single<AdvertisementDao> { AdvertisementDaoImpl() }
    single<AdCommentDao> { AdCommentDaoImpl() }
}
