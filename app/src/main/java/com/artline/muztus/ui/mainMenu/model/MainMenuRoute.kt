package com.artline.muztus.ui.mainMenu.model

import com.artline.muztus.R
import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.ComposeRouteNavigation
import com.muztus.core_mvi.ComposeRouteOpenWeb


sealed class MainMenuRoute : ComposeFragmentRoute {
    object GoMainGame : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.premiaSelectFragment
    }

    object GoStatistic : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.statisticScreenFragment
    }

    object GoShop : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.shopFragment
    }

    object GoCreators : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.creatorsFragment
    }

    data class OpenSocials(private val webLink: String) : MainMenuRoute(), ComposeRouteOpenWeb {
        override fun getWebUrl(): String = webLink
    }
}
