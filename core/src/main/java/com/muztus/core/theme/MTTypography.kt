package com.muztus.core.theme

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
)