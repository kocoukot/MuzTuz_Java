package com.muztus.core.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

object MTTheme {
    val colors: MTColors
        @Composable get() = LocalMTColor.current
    val typography: MTTypography
        @Composable get() = LocalMTTypography.current


}

internal val LocalMTColor = compositionLocalOf { MTColors() }
internal val LocalMTTypography = compositionLocalOf { MTTypography() }

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.bigSpace(): Modifier = composed {
    this.padding(horizontal = 44.dp)
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.mediumSpace(): Modifier = composed {
    this.padding(horizontal = 28.dp)
}