package com.artline.muztus.ui.mainMenu.model

import com.artline.muztus.R
import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.ComposeRouteNavigation


sealed class MainMenuRoute : ComposeFragmentRoute {
    object GoMainGame : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.premiaSelectFragment
    }

    object GoStatistic : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.statisticScreenFragment
    }

//    object GoShop : MainMenuRoute() {
//        override fun handle(route: MainMenuRoute) = route.navigateShop()
//    }

    object GoCreators : MainMenuRoute(), ComposeRouteNavigation.GraphNavigate {
        override fun destination(): Int = R.id.creatorsFragment
    }
}
