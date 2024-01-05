package com.example.colorscaler

import androidx.compose.ui.graphics.Color

data class ColorModel(
    val type: String,
    val color: Color,
    val parameterNames: List<String>,
    val parameterValues: List<String>,
    val parameterRanges: List<String>
)