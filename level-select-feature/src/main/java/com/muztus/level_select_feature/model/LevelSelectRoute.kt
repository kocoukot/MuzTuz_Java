package com.muztus.level_select_feature.model

import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.ComposeRouteNavigation
import com.muztus.level_select_feature.R

sealed class LevelSelectRoute : ComposeFragmentRoute {
    data class GoGameLevel(private val selectedPremium: Int, private val selectedLevel: Int) :
        LevelSelectRoute(), ComposeRouteNavigation.DeepLinkNavigate {
        override fun destination(): Int = R.string.deep_link_game_level_fragment
        override val arguments: String = "${selectedPremium}/${selectedLevel}"
    }
}