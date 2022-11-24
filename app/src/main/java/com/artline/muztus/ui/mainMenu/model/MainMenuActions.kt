package com.artline.muztus.ui.mainMenu.model


interface MainMenuActions {
    fun showResetAlert()
    fun navigateScreen(route: MainMenuRoute)
    fun resetStatistic(isReset: Boolean)
    fun closeFirstLaunchAlert()
    fun clickSocials(webLink: String)


    sealed class MainMenuAction {
        abstract fun handle(action: MainMenuActions)

        object ClickOnMainGame : MainMenuAction() {
            override fun handle(action: MainMenuActions) =
                action.navigateScreen(MainMenuRoute.GoMainGame)
        }

        object ClickOnGoStatistic : MainMenuAction() {
            override fun handle(action: MainMenuActions) =
                action.navigateScreen(MainMenuRoute.GoStatistic)
        }

        object ClickOnResetStatistic : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.showResetAlert()
        }

        data class OnResetStatisticDecision(private val isReset: Boolean) : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.resetStatistic(isReset)
        }

        object ClickOnShowCreators : MainMenuAction() {
            override fun handle(action: MainMenuActions) =
                action.navigateScreen(MainMenuRoute.GoCreators)
        }

        object CloseFirstLaunchAlert : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.closeFirstLaunchAlert()
        }

        object ClickOnGoShop : MainMenuAction() {
            override fun handle(action: MainMenuActions) =
                action.navigateScreen(MainMenuRoute.GoShop)
        }

        data class OnSocialSelect(private val socialLink: String) : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.clickSocials(socialLink)
        }
    }
}