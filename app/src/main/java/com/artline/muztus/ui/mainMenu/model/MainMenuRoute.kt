package com.artline.muztus.ui.mainMenu.model

import com.artline.muztus.R
import com.artline.muztus.sounds.GameSound
import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.ComposeRouteNavigation
import com.muztus.core_mvi.ComposeRouteOpenWeb


sealed class MainMenuRoute : ComposeFragmentRoute {
    object GoMainGame : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.action_mainMenuFragment_to_premiaSelectFragment
    }

    object GoStatistic : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.action_mainMenuFragment_to_statisticScreenFragment
    }

    object GoShop : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.action_mainMenuFragment_to_shopFragment
    }

    object GoCreators : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.action_mainMenuFragment_to_creatorsFragment
    }

    data class OpenSocials(private val webLink: String) : MainMenuRoute(), ComposeRouteOpenWeb {
        override fun getWebUrl(): String = webLink
    }

    data class PlaySound(val soundType: GameSound) : MainMenuRoute()

    object ClearStarts : MainMenuRoute()
}
