package com.example.parkingspotfinder.presentation.map_screen

import com.example.parkingspotfinder.domain.model.ParkingSpot
import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties: MapProperties = MapProperties(),
    val parkingSpots: List<ParkingSpot> = emptyList(),
    val isFalloutMap: Boolean = false
)