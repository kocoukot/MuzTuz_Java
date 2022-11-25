package com.muztus.game_level_feature.model

import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.ComposeRouteNavigation

sealed class GameLevelRoute : ComposeFragmentRoute {
    object UpdateCoins : GameLevelRoute()

    object CloseGameLevel : GameLevelRoute(), ComposeRouteNavigation.PopNavigation

}
