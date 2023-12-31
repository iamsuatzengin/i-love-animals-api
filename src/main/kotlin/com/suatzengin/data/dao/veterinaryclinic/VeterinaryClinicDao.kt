package com.suatzengin.data.dao.veterinaryclinic

import com.suatzengin.data.dao.Dao
import com.suatzengin.data.request.veterinaryclinic.VeterinaryClinicRequest
import com.suatzengin.model.VeterinaryClinic

interface VeterinaryClinicDao: Dao<VeterinaryClinic> {

    suspend fun getAllVeterinaryClinics(): List<VeterinaryClinic>

    suspend fun getVeterinaryClinics(postalCode: String): List<VeterinaryClinic>

    suspend fun addVeterinaryClinic(veterinaryClinicRequest: VeterinaryClinicRequest)
}