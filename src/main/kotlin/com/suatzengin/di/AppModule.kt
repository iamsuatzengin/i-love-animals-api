package com.suatzengin.di

import com.suatzengin.data.dao.CompleteAdvertisementController
import com.suatzengin.data.dao.adcomment.AdCommentDao
import com.suatzengin.data.dao.adcomment.AdCommentDaoImpl
import com.suatzengin.data.dao.advertisement.AdvertisementDao
import com.suatzengin.data.dao.advertisement.AdvertisementDaoImpl
import com.suatzengin.data.dao.auth.AuthDao
import com.suatzengin.data.dao.auth.AuthDaoImpl
import com.suatzengin.data.dao.charityscore.CharityScoreDao
import com.suatzengin.data.dao.charityscore.CharityScoreDaoImpl
import com.suatzengin.data.dao.profile.ProfileDao
import com.suatzengin.data.dao.profile.ProfileDaoImpl
import com.suatzengin.data.dao.veterinaryclinic.VeterinaryClinicDao
import com.suatzengin.data.dao.veterinaryclinic.VeterinaryClinicDaoImpl
import org.koin.dsl.module

val appModule = module {
    single<AuthDao> { AuthDaoImpl() }
    single<AdvertisementDao> { AdvertisementDaoImpl() }
    single<AdCommentDao> { AdCommentDaoImpl() }
    single<ProfileDao> { ProfileDaoImpl() }
    single<CharityScoreDao> { CharityScoreDaoImpl() }
    single<VeterinaryClinicDao> { VeterinaryClinicDaoImpl() }
    single { CompleteAdvertisementController(
        advertisementDao = get(),
        charityScoreDao = get()
    ) }
}
