package com.artline.muztus.ui.mainMenu.model


interface MainMenuActions {
    fun goMainGame()
    fun showStatistic()
    fun showResetAlert()

    fun resetStatistic(isReset: Boolean)
    fun showCredits()

    sealed class MainMenuAction {
        abstract fun handle(action: MainMenuActions)

        object ClickOnMainGame : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.goMainGame()
        }

        object ClickOnGoStatistic : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.showStatistic()
        }

        object ClickOnResetStatistic : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.showResetAlert()
        }

        data class OnResetStatisticDecision(private val isReset: Boolean) : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.resetStatistic(isReset)
        }

        object ClickOnShowCreators : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.showCredits()
        }

//        object ClickOnGoShop : MainMenuAction() {
//            override fun handle(action: MainMenuActions) = action.goShop()
//        }
    }
}