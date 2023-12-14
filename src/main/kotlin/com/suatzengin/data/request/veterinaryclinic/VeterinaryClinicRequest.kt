package com.suatzengin.data.request.veterinaryclinic

import kotlinx.serialization.Serializable

@Serializable
data class VeterinaryClinicRequest(
    val clinicName: String,
    val doctorName: String,
    val longitude: String,
    val latitude: String,
    val address: String,
    val postalCode: String,
    val openTimes: String,
    val closeTimes: String,
    val isAmbulanceAvailable: Boolean,
    val images: Array<String>,
    val phoneNumber: String
)
