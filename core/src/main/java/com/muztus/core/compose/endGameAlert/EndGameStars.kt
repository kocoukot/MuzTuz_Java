package com.muztus.core.compose.endGameAlert

import androidx.annotation.DrawableRes
import com.muztus.core.R

enum class EndGameStars(@DrawableRes val openImage: Int, @DrawableRes val closedImage: Int) {
    FIRST_STAR(R.drawable.zvezda1_prizovogo_okna, R.drawable.zvezda_zakrita1_prizovogo_okna),
    CENTER_STAR(R.drawable.zvezda2_prizovogo_okna, R.drawable.zvezda_zakrita2_prizovogo_okna),
    END_STAR(R.drawable.zvezda3_prizovogo_okna, R.drawable.zvezda_zakrita3_prizovogo_okna)
}