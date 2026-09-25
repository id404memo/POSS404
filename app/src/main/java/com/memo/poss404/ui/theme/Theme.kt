
package com.memo.poss404.ui.theme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF0A2540), secondary = Color(0xFFFF6B00), tertiary = Color(0xFFFFC300),
    background = Color(0xFF0A0F1E), surface = Color(0xFF121A2B)
)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0A2540), secondary = Color(0xFFFF6B00), tertiary = Color(0xFFFFC300),
    background = Color(0xFFF6F7FB), surface = Color.White
)
@Composable
fun POSS404Theme(content: @Composable ()->Unit){
    MaterialTheme(colorScheme = LightColorScheme, content = content)
}
