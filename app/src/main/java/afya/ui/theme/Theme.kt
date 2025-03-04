package com.example.afya.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Art Style: Vibrant and Playful

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF9C27B0), // Deep Purple
    secondary = Color(0xFFFF9800), // Orange
    tertiary = Color(0xFF4CAF50), // Green
    background = Color(0xFFFFF3E0), // Light Orange-Yellow
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = Color(0xFFD32F2F), // Dark Red
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFCE93D8), // Light Purple
    secondary = Color(0xFFFFCC80), // Light Orange
    tertiary = Color(0xFFA5D6A7), // Light Green
    background = Color(0xFF1E1E1E), // Dark Gray
    surface = Color(0xFF303030), // Slightly Lighter Dark Gray
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color(0xFFEF9A9A), // Light Red
    onError = Color.Black
)

@Composable
fun AfyaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}