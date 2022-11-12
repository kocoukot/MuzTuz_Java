package com.artline.muztus.ui.mainMenu.model

import com.muztus.core_mvi.ComposeFragmentState

data class MainMenuState(
    private val error: String = "",
    private val isLoading: Boolean = false
) : ComposeFragmentState
