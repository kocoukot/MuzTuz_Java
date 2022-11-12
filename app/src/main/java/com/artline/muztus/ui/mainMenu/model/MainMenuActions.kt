package com.artline.muztus.ui.mainMenu.model


interface MainMenuActions {
    fun goMainGame()
    fun showStatistic()
    fun resetStatistic()
    fun showCredits()

    sealed class MainMenuAction {
        abstract fun handle(action: MainMenuActions)

        object ClickOnMainGame : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.goMainGame()
        }

        object ClickOnShowStatistic : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.showStatistic()
        }

        object ClickOnResetStatistic : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.resetStatistic()
        }

        object ClickOnShowCreators : MainMenuAction() {
            override fun handle(action: MainMenuActions) = action.showCredits()
        }

//        object ClickOnGoShop : MainMenuAction() {
//            override fun handle(action: MainMenuActions) = action.goShop()
//        }
    }
}