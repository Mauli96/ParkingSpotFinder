package com.example.parkingspotfinder.presentation.map_screen.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parkingspotfinder.R

@Composable
fun AnimatedThemeToggleFAB(
    isFalloutMap: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isAnimating by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if(isAnimating) 360f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        finishedListener = {
            isAnimating = false
        }
    )

    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 1.2f else 1f,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    )

    FloatingActionButton(
        modifier = modifier
            .scale(scale)
            .rotate(rotation),
        onClick = {
            isAnimating = true
            onToggle()
        },
        shape = CircleShape
    ) {
        Image(
            painter = if(isFalloutMap) {
                painterResource(R.drawable.ic_toggle_on)
            } else {
                painterResource(R.drawable.ic_toggle_off)
            },
            contentDescription = stringResource(R.string.toggle_fallout_map),
            modifier = Modifier.size(20.dp)
        )
    }
}