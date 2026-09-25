
package com.memo.poss404.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Blue = Color(0xFF0071E3)
private val Bg = Color(0xFFF5F5F7)
private val BlackText = Color(0xFF1D1D1F)
private val GrayText = Color(0xFF86868B)
private val White = Color(0xFFFFFFFF)
private val Orange = Color(0xFFFF9500)

private val LightColors = lightColorScheme(
    primary = Blue,
    onPrimary = White,
    background = Bg,
    onBackground = BlackText,
    surface = White,
    onSurface = BlackText,
    surfaceVariant = Bg,
    secondary = GrayText,
    tertiary = Orange
)

@Composable
fun POSS404Theme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColors, content = content)
}
