package com.muztus.domain_layer.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.muztus.domain_layer.R

interface PremiaLevelModel {
    @Composable
    fun LevelImage(modifier: Modifier, onSelect: (Int) -> Unit)

    fun levelIndex(): Int

    fun levelInfo(): Base

    fun setPassed(): Base

    fun isPassed(): Boolean

    data class Base(
        val levelIndex: Int,
        val isLevelPassed: Boolean,
        val levelImage: Int,
    ) : PremiaLevelModel {

        @Composable
        override fun LevelImage(modifier: Modifier, onSelect: (Int) -> Unit) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .heightIn(max = 120.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onSelect.invoke(levelIndex)
                    },
                contentAlignment = Alignment.Center
            ) {

                Image(
                    alignment = Alignment.Center,

                    modifier = modifier
                        .fillMaxHeight(),
                    painter = painterResource(id = levelImage),
                    contentDescription = null
                )
                if (isLevelPassed)
                    Image(
                        alignment = Alignment.Center,
                        modifier = modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillWidth,
                        painter = painterResource(id = R.drawable.level_solved),
                        contentDescription = null
                    )
            }

        }

        override fun levelIndex(): Int = levelIndex
        override fun levelInfo(): Base = this
        override fun setPassed(): Base = this.copy(isLevelPassed = true)
        override fun isPassed(): Boolean = isLevelPassed


    }
}