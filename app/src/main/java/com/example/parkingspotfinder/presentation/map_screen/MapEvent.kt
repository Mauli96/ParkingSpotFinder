package com.example.parkingspotfinder.presentation.map_screen

import com.example.parkingspotfinder.domain.model.ParkingSpot
import com.google.android.gms.maps.model.LatLng

sealed interface MapEvent {
    data class OnMapLongClick(val latLng: LatLng): MapEvent
    data class OnInfoWindowLongClick(val spot: ParkingSpot): MapEvent
    data object OnPermissionDialogShow: MapEvent
    data object OnPermissionDialogDismiss : MapEvent
}