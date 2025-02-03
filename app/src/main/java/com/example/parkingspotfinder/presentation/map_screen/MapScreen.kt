package com.example.parkingspotfinder.presentation.map_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.parkingspotfinder.R
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings

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
            uiSettings = uiSettings
        )

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                viewModel.onEvent(MapEvent.ToggleFalloutMap)
            },
            shape = CircleShape
        ) {
            Image(
                painter = if(mapState.isFalloutMap) {
                    painterResource(R.drawable.ic_toggle_on)
                } else {
                    painterResource(R.drawable.ic_toggle_off)
                },
                contentDescription = stringResource(R.string.toggle_fallout_map),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}