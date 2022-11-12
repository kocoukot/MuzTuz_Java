package com.artline.muztus.ui.mainMenu

import com.artline.muztus.ui.mainMenu.model.MainMenuActions
import com.artline.muztus.ui.mainMenu.model.MainMenuRoute
import com.artline.muztus.ui.mainMenu.model.MainMenuState
import com.muztus.core_mvi.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainMenuViewModel : BaseViewModel.Base<MainMenuState, MainMenuActions.MainMenuAction>(
    mState = MutableStateFlow(MainMenuState())
), MainMenuActions {


    override fun setInputActions(action: MainMenuActions.MainMenuAction) {
        action.handle(this)
    }

    override fun goMainGame() {
        sendRoute(MainMenuRoute.GoMainGame)
    }

    override fun showStatistic() {
        sendRoute(MainMenuRoute.GoStatistic)
    }

    override fun resetStatistic() {

    }

    override fun showCredits() {
        sendRoute(MainMenuRoute.GoCreators)

    }
}