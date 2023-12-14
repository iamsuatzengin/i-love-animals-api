package com.suatzengin.model

import com.suatzengin.util.columntype.array
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.TextColumnType

@Serializable
data class VeterinaryClinic(
    val id: Long,
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
    val phoneNumber: String,
)

object VeterinaryClinicTable: Table() {
    val id = long(name = "id").autoIncrement()
    val clinicName = text(name = "clinic_name")
    val doctorName = varchar(name = "doctor_name", length = 50)
    val longitude = varchar(name = "longitude", length = 50)
    val latitude = varchar(name = "latitude", length = 50)
    val address = text(name = "address")
    val postalCode = varchar(name = "postal_code", length = 6)
    val openTimes = varchar(name = "open_times", length = 6)
    val closeTimes = varchar(name = "close_times", length = 6)
    val isAmbulanceAvailable = bool(name = "is_ambulance_available")
    val images = array<String>(name = "images", TextColumnType())
    val phoneNumber = varchar("phone_number", length = 16)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
