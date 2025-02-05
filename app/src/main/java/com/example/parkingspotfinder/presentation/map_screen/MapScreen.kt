package com.example.parkingspotfinder.presentation.map_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    mapState: MapState,
    onEvent: (MapEvent) -> Unit
) {

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(key1 = mapState.currentLocation) {
        mapState.currentLocation?.let { location ->
            cameraPositionState.centerOnLocation(
                LatLng(location.latitude, location.longitude)
            )
        }
    }

    val properties = remember(mapState.hasLocationPermission) {
        mapState.properties.copy(
            isMyLocationEnabled = mapState.hasLocationPermission
        )
    }

    val uiSettings = remember(mapState.hasLocationPermission) {
        MapUiSettings(
            zoomControlsEnabled = true,
            myLocationButtonEnabled = mapState.hasLocationPermission,
            mapToolbarEnabled = true
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                onEvent(MapEvent.OnMapLongClick(it))
            },
            contentPadding = PaddingValues(
                top = 25.dp,
                bottom = 16.dp,
                end = 16.dp
            )
        ) {
            mapState.parkingSpots.forEach { spot ->
                Marker(
                    position = LatLng(spot.lat, spot.lng),
                    title = "Parking spot (%.4f, %.4f)".format(spot.lat, spot.lng),
                    snippet = "Long click to delete",
                    onInfoWindowLongClick = {
                        it.hideInfoWindow()
                        onEvent(
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
    }
}

private suspend fun CameraPositionState.centerOnLocation(
    location: LatLng,
    zoom: Float = 15f,
    tilt: Float = 0f,
    bearing: Float = 0f
) {
    animate(
        update = CameraUpdateFactory.newCameraPosition(
            CameraPosition(location, zoom, tilt, bearing)
        )
    )
}