
package com.memo.poss404.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme()

@Composable
fun POSS404Theme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColors, content = content)
}
