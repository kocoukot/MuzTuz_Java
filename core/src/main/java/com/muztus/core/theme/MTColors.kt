package com.muztus.core.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color


//data class SMTypography internal constructor
data class MTColors internal constructor(
    val background: Color = Color(0xFF4B8B71),
    val black: Color = Color(0xFF000000),

    val alertBackground: Color = Color(0xFFF5E6C9),
    val buttonPressed: Color = Color(0xFFFFFBDB),
    val mainDarkBrown: Color = Color(0xFF2B0103),
    val buttonPressedText: Color = Color(0xFF4B8E6F),

    val buttonDisabled: Color = Color(0xFF4D4D4D),


    val white: Color = Color(0xFFFFFFFF),
)

val MaterialSelectionColor = lightColors(
    primary = Color(0xFF000000),
)