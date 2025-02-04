package com.example.parkingspotfinder.presentation.map_screen.components

object MapStyle {

    val json = """
        [
            {
                "featureType": "all",
                "elementType": "labels.text.fill",
                "stylers": [
                    { "color": "#ffffff" },
                    { "lightness": 60 }
                ]
            },
            {
                "featureType": "all",
                "elementType": "labels.text.stroke",
                "stylers": [
                    { "color": "#222222" },
                    { "lightness": 10 }
                ]
            },
            {
                "featureType": "landscape",
                "elementType": "geometry",
                "stylers": [
                    { "color": "#1b2b34" }
                ]
            },
            {
                "featureType": "road.highway",
                "elementType": "geometry.fill",
                "stylers": [
                    { "color": "#2c3e50" },
                    { "lightness": 20 }
                ]
            },
            {
                "featureType": "road.highway",
                "elementType": "geometry.stroke",
                "stylers": [
                    { "color": "#1a252f" }
                ]
            },
            {
                "featureType": "road.arterial",
                "elementType": "geometry.fill",
                "stylers": [
                    { "color": "#34495e" }
                ]
            },
            {
                "featureType": "road.local",
                "elementType": "geometry.fill",
                "stylers": [
                    { "color": "#2c3e50" }
                ]
            },
            {
                "featureType": "poi.park",
                "elementType": "geometry",
                "stylers": [
                    { "color": "#1e5631" }
                ]
            },
            {
                "featureType": "water",
                "elementType": "geometry",
                "stylers": [
                    { "color": "#0e3749" }
                ]
            },
            {
                "featureType": "transit",
                "elementType": "geometry",
                "stylers": [
                    { "color": "#2c3e50" }
                ]
            }
        ]
    """.trimIndent()
}