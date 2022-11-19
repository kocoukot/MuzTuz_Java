package com.artline.muztus.ui.mainMenu.model

import com.muztus.core_mvi.ComposeFragmentState

data class MainMenuState(
    val error: String = "",
    val isLoading: Boolean = false,
    val showResetAlert: Boolean = false


) : ComposeFragmentState
