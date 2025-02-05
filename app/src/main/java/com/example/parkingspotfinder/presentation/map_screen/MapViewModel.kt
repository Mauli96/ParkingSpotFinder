package com.example.parkingspotfinder.presentation.map_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingspotfinder.domain.location.LocationTracker
import com.example.parkingspotfinder.domain.model.ParkingSpot
import com.example.parkingspotfinder.domain.repository.ParkingSpotRepository
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: ParkingSpotRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _mapState = MutableStateFlow(MapState())
    val mapState = _mapState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getParkingSpots().collectLatest { spots ->
                _mapState.update {
                    it.copy(
                        parkingSpots = spots
                    )
                }
            }
        }
    }

    fun onEvent(event: MapEvent) {
        when(event) {
            is MapEvent.OnMapLongClick -> {
                viewModelScope.launch {
                    repository.insertParkingSpot(
                        ParkingSpot(
                            event.latLng.latitude,
                            event.latLng.longitude
                        )
                    )
                }
            }
            is MapEvent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    repository.deleteParkingSpot(event.spot)
                }
            }
            is MapEvent.OnPermissionDialogShow -> {
                _mapState.update {
                    it.copy(
                        showPermissionDialog = true
                    )
                }
            }
            is MapEvent.OnPermissionDialogDismiss -> {
                _mapState.update {
                    it.copy(
                        showPermissionDialog = false
                    )
                }
            }
        }
    }

    fun onLocationPermissionsResult(granted: Boolean) {
        _mapState.update {
            it.copy(
                hasLocationPermission = granted,
                showPermissionDialog = !granted
            )
        }
        if(granted) {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { location ->
                _mapState.update {
                    it.copy(
                        currentLocation = location
                    )
                }
            }
        }
    }
}