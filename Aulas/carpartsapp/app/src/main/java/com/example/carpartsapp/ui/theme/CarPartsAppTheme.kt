package com.example.carpartsapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.Shapes
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CarPartsAppTheme(content: @Composable () -> Unit) {
    val colors = lightColors(
        primary = Color(0xFF6200EE),
        primaryVariant = Color(0xFF3700B3),
        secondary = Color(0xFF03DAC6),
        // Adicione outras cores conforme necessário
    )

    val typography = Typography(
        // Configurar tipografia conforme necessário
    )

    val shapes = Shapes(
        // Definir formas customizadas aqui, ou usar as padrões
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
