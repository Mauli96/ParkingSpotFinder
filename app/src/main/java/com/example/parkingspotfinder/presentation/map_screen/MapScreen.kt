package com.example.parkingspotfinder.presentation.map_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.parkingspotfinder.presentation.map_screen.components.AnimatedThemeToggleFAB
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {

    val mapState by viewModel.mapState.collectAsState()
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = mapState.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                viewModel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            mapState.parkingSpots.forEach { spot ->
                Marker(
                    position = LatLng(spot.lat, spot.lng),
                    title = "Parking spot (${spot.lat}, ${spot.lng})",
                    snippet = "Long click to delete",
                    onInfoWindowLongClick = {
                        it.hideInfoWindow()
                        viewModel.onEvent(
                            MapEvent.OnInfoWindowLongClick(spot)
                        )
                    },
                    onClick = {
                        it.showInfoWindow()
                        true
                    },
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN
                    )
                )
            }
        }

        AnimatedThemeToggleFAB(
            isFalloutMap = mapState.isFalloutMap,
            onToggle = {
                viewModel.onEvent(MapEvent.ToggleFalloutMap)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}