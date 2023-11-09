package com.suatzengin.plugins

import com.suatzengin.data.dao.adcomment.AdCommentDao
import com.suatzengin.data.dao.advertisement.AdvertisementDao
import com.suatzengin.data.dao.auth.AuthDao
import com.suatzengin.data.dao.charityscore.CharityScoreDao
import com.suatzengin.data.dao.profile.ProfileDao
import com.suatzengin.routes.adcomments.addAdvertisementComment
import com.suatzengin.routes.adcomments.deleteAdvertisementComment
import com.suatzengin.routes.adcomments.getAdvertisementComments
import com.suatzengin.routes.adcomments.updateAdvertisementComment
import com.suatzengin.routes.advertisement.*
import com.suatzengin.routes.auth.loginRoute
import com.suatzengin.routes.auth.registerRoute
import com.suatzengin.routes.charityscore.getCharityScores
import com.suatzengin.routes.charityscore.updateCharityScore
import com.suatzengin.routes.profile.getUserProfile
import com.suatzengin.routes.profile.updateUserProfile
import com.suatzengin.routes.search.searchAdvertisement
import com.suatzengin.util.extensions.configureJWTConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val authDao by inject<AuthDao>()
    val advertisementDao by inject<AdvertisementDao>()
    val adCommentDao by inject<AdCommentDao>()
    val profileDao by inject<ProfileDao>()
    val charityScoreDao by inject<CharityScoreDao>()
    val jwtConfig = configureJWTConfig()

    routing {

        // Auth routes
        registerRoute(
            authDao = authDao,
            profileDao = profileDao,
            charityScoreDao = charityScoreDao
        )
        loginRoute(dao = authDao, jwtConfig = jwtConfig)

        // Advertisement routes
        getAllAdvertisement(dao = advertisementDao)
        addAdvertisement(dao = advertisementDao)
        getAdvertisement(dao = advertisementDao)
        updateAdvertisement(dao = advertisementDao)
        deleteAdvertisement(dao = advertisementDao)

        // Search Advertisement
        searchAdvertisement(dao = advertisementDao)

        // Advertisement comments routes
        getAdvertisementComments(dao = adCommentDao)
        addAdvertisementComment(dao = adCommentDao)
        updateAdvertisementComment(dao = adCommentDao)
        deleteAdvertisementComment(dao = adCommentDao)

        // User profile
        getUserProfile(profileDao = profileDao)
        updateUserProfile(profileDao = profileDao)

        // Charity score
        getCharityScores(charityScoreDao = charityScoreDao)
        updateCharityScore(charityScoreDao = charityScoreDao)
    }
}
