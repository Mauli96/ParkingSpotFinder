package com.example.parkingspotfinder.presentation.map_screen

sealed interface MapEvent {
    data object ToggleFalloutMap: MapEvent
}