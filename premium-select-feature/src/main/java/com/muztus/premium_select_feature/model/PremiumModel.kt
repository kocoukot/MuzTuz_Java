package com.muztus.premium_select_feature.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

interface PremiumModel {

    @Composable
    fun SetPremiaImage(modifier: Modifier, onSelect: (PremiaSelectActions) -> Unit)

    fun premiumNumber(): Int
    data class Base(
        private val premiumNumber: Int,
        private val premiumProgress: Int,
        private val premiumLvlAmount: Int,
        private val premiumImage: Int,
    ) : PremiumModel {

        @Composable
        override fun SetPremiaImage(modifier: Modifier, onSelect: (PremiaSelectActions) -> Unit) {
            Image(
                modifier = modifier
                    .size(200.dp)
                    .shadow(8.dp, shape = CircleShape)
                    .clip(CircleShape)
                    .clickable {
                        onSelect.invoke(
                            PremiaSelectActions.SelectedPremia(this)
                        )
                    },
                painter = painterResource(id = premiumImage),
                contentDescription = null
            )
        }

        override fun premiumNumber(): Int = premiumNumber
    }
}
