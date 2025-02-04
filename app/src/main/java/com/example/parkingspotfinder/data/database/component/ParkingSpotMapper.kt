package com.example.parkingspotfinder.data.database.component

import com.example.parkingspotfinder.data.database.ParkingSpotEntity
import com.example.parkingspotfinder.domain.model.ParkingSpot

fun ParkingSpotEntity.toParkingSpot(): ParkingSpot {
    return ParkingSpot(
        lat = lat,
        lng = lng,
        id = id
    )
}

fun ParkingSpot.toParkingSpotEntity(): ParkingSpotEntity {
    return ParkingSpotEntity(
        lat = lat,
        lng = lng,
        id = id
    )
}