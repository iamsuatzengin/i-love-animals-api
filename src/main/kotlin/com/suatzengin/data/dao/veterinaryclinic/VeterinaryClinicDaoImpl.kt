package com.suatzengin.data.dao.veterinaryclinic

import com.suatzengin.data.request.veterinaryclinic.VeterinaryClinicRequest
import com.suatzengin.model.VeterinaryClinic
import com.suatzengin.model.VeterinaryClinicTable
import com.suatzengin.util.extensions.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class VeterinaryClinicDaoImpl : VeterinaryClinicDao {

    override fun resultRow(row: ResultRow) = VeterinaryClinic(
        id = row[VeterinaryClinicTable.id],
        clinicName = row[VeterinaryClinicTable.clinicName],
        doctorName = row[VeterinaryClinicTable.doctorName],
        longitude = row[VeterinaryClinicTable.longitude],
        latitude = row[VeterinaryClinicTable.latitude],
        address = row[VeterinaryClinicTable.address],
        postalCode = row[VeterinaryClinicTable.postalCode],
        openTimes = row[VeterinaryClinicTable.openTimes],
        closeTimes = row[VeterinaryClinicTable.closeTimes],
        isAmbulanceAvailable = row[VeterinaryClinicTable.isAmbulanceAvailable],
        images = row[VeterinaryClinicTable.images],
        phoneNumber = row[VeterinaryClinicTable.phoneNumber]
    )

    override suspend fun getVeterinaryClinics(postalCode: String): List<VeterinaryClinic> = dbQuery {
        VeterinaryClinicTable.select {
            VeterinaryClinicTable.postalCode eq postalCode
        }
            .map(::resultRow)
            .toList()
    }

    override suspend fun addVeterinaryClinic(veterinaryClinicRequest: VeterinaryClinicRequest) = dbQuery {
        VeterinaryClinicTable.insert { statement ->
            statement[clinicName] = veterinaryClinicRequest.clinicName
            statement[doctorName] = veterinaryClinicRequest.doctorName
            statement[longitude] = veterinaryClinicRequest.longitude
            statement[latitude] = veterinaryClinicRequest.latitude
            statement[address] = veterinaryClinicRequest.address
            statement[postalCode] = veterinaryClinicRequest.postalCode
            statement[openTimes] = veterinaryClinicRequest.openTimes
            statement[closeTimes] = veterinaryClinicRequest.closeTimes
            statement[isAmbulanceAvailable] = veterinaryClinicRequest.isAmbulanceAvailable
            statement[images] = veterinaryClinicRequest.images
            statement[phoneNumber] = veterinaryClinicRequest.phoneNumber
        }

        Unit
    }
}

