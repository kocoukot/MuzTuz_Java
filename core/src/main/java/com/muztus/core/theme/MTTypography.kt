package com.muztus.core.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


data class MTTypography internal constructor(


    val big_title: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 34.sp,
//        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.W700,
        letterSpacing = (-0.01).sp,
    ),

    val informText: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 20.sp,

        fontWeight = FontWeight.W600,
//        letterSpacing = (-0.01).sp,
        color = Color(0xFF2B0103)
    ),
)