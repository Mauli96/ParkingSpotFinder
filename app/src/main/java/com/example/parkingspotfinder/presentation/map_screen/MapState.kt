package com.example.parkingspotfinder.presentation.map_screen

import android.location.Location
import com.example.parkingspotfinder.domain.model.ParkingSpot
import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties: MapProperties = MapProperties(),
    val parkingSpots: List<ParkingSpot> = emptyList(),
    val currentLocation: Location? = null,
    val isFalloutMap: Boolean = false,
    val hasLocationPermission: Boolean = false,
    val showPermissionDialog: Boolean = false
)