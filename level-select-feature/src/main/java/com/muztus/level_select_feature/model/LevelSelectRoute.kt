package com.muztus.level_select_feature.model

import com.artline.muztus.sounds.GameSound
import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.ComposeRouteNavigation

sealed class LevelSelectRoute : ComposeFragmentRoute {
    object GoBack : LevelSelectRoute(), ComposeRouteNavigation.PopNavigation
    object UpdateCoins : LevelSelectRoute()

    data class PlaySound(val soundType: GameSound) : LevelSelectRoute()

    object GetFreeCoins : LevelSelectRoute()

}