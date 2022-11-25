package com.muztus.game_level_feature.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


interface GameToast {

    @Composable
    fun toastText(): String


    class ToastInfo(
        private val toastText: Int
    ) : GameToast {

        @Composable
        override fun toastText(): String = stringResource(id = toastText)
    }

    class ToastWithArgs(
        private val toastText: Int, private val argument: Int
    ) : GameToast {

        @Composable
        override fun toastText(): String = stringResource(id = toastText, argument)
    }

    object Empty : GameToast {

        @Composable
        override fun toastText(): String = ""
    }

}

