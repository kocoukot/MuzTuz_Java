package com.muztus.premium_select_feature.model

import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.ComposeRouteNavigation
import com.muztus.premium_select_feature.R

sealed class PremiumSelectRoute : ComposeFragmentRoute {
    data class GoLevelSelect(private val premiumIndex: Int) : PremiumSelectRoute(),
        ComposeRouteNavigation.DeepLinkNavigate {
        override fun destination(): Int = R.string.deep_link_level_fragment
        override val arguments: String = "$premiumIndex"
    }
}