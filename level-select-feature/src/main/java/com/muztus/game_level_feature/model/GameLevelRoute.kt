package com.muztus.game_level_feature.model

import com.muztus.core_mvi.ComposeFragmentRoute

sealed class GameLevelRoute : ComposeFragmentRoute {
    object UpdateCoins : GameLevelRoute()

}
