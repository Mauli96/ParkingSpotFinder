package com.example.parkingspotfinder.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParkingSpotEntity(
    val lat: Double,
    val lng: Double,
    @PrimaryKey val id: Int? = null
)