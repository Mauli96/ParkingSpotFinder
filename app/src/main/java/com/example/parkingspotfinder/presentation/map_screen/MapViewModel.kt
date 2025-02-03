package com.example.parkingspotfinder.presentation.map_screen

import androidx.lifecycle.ViewModel
import com.example.parkingspotfinder.presentation.map_screen.util.MapStyle
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(

) : ViewModel() {

    private val _mapState = MutableStateFlow(MapState())
    val mapState = _mapState.asStateFlow()

    fun onEvent(event: MapEvent) {
        when(event) {
            MapEvent.ToggleFalloutMap -> {
                _mapState.update {
                    it.copy(
                        properties = it.properties.copy(
                            mapStyleOptions = if(it.isFalloutMap) {
                                null
                            } else {
                                MapStyleOptions(MapStyle.json)
                            }
                        ),
                        isFalloutMap = !it.isFalloutMap
                    )
                }
            }
        }
    }
}