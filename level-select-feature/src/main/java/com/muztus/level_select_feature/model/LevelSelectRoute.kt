package com.muztus.level_select_feature.model

import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.ComposeRouteNavigation

sealed class LevelSelectRoute : ComposeFragmentRoute {
    object GoBack : LevelSelectRoute(), ComposeRouteNavigation.PopNavigation
    object UpdateCoins : LevelSelectRoute()

}