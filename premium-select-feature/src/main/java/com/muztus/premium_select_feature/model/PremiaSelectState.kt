package com.muztus.premium_select_feature.model

import com.muztus.core_mvi.ComposeFragmentState

data class PremiaSelectState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: List<PremiumModel> = emptyList()
) : ComposeFragmentState
