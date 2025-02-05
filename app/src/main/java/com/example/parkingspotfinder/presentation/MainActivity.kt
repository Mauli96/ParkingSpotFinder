package com.example.parkingspotfinder.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.parkingspotfinder.presentation.map_screen.MapEvent
import com.example.parkingspotfinder.presentation.map_screen.MapScreen
import com.example.parkingspotfinder.presentation.map_screen.MapViewModel
import com.example.parkingspotfinder.presentation.map_screen.components.PermissionDialog
import com.example.parkingspotfinder.presentation.ui.theme.ParkingSpotFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MapViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupPermissionLauncher()
        checkLocationPermissions()

        enableEdgeToEdge()

        setContent {
            ParkingSpotFinderTheme {
                val mapState by viewModel.mapState.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(mapState.showPermissionDialog) {
                        PermissionDialog(
                            onDismiss = {
                                viewModel.onEvent(MapEvent.OnPermissionDialogDismiss)
                            },
                            onOkClick = {
                                viewModel.onEvent(MapEvent.OnPermissionDialogDismiss)
                                requestLocationPermissions()
                            }
                        )
                    }

                    MapScreen(
                        mapState = mapState,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }

    private fun setupPermissionLauncher() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val locationPermissionsGranted = permissions.entries.all { it.value }
            viewModel.onLocationPermissionsResult(locationPermissionsGranted)
        }
    }

    private fun checkLocationPermissions() {
        val permissions = REQUIRED_LOCATION_PERMISSIONS.map { permission ->
            checkSelfPermission(permission) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }
        viewModel.onLocationPermissionsResult(permissions.all { it })
    }

    private fun requestLocationPermissions() {
        permissionLauncher.launch(REQUIRED_LOCATION_PERMISSIONS)
    }

    companion object {
        private val REQUIRED_LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}