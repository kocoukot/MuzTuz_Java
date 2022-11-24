package com.artline.muztus.ui.mainMenu

import com.artline.muztus.ui.mainMenu.model.MainMenuActions
import com.artline.muztus.ui.mainMenu.model.MainMenuRoute
import com.artline.muztus.ui.mainMenu.model.MainMenuState
import com.muztus.core_mvi.BaseViewModel
import com.muztus.domain_layer.usecase.IsFirstLaunchUseCase
import com.muztus.domain_layer.usecase.ResetStatisticUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class MainMenuViewModel(
    private val isFirstLaunchUseCase: IsFirstLaunchUseCase,
    private val resetStatisticUseCase: ResetStatisticUseCase,
) : BaseViewModel.Base<MainMenuState, MainMenuActions.MainMenuAction>(
    mState = MutableStateFlow(MainMenuState())
), MainMenuActions {

    init {
        updateInfo { copy(showFirstLaunchAlert = isFirstLaunchUseCase.invoke()) }
    }

    override fun setInputActions(action: MainMenuActions.MainMenuAction) {
        action.handle(this)
    }

    override fun showResetAlert() {
        updateInfo { copy(showResetAlert = true) }
    }

    override fun navigateScreen(route: MainMenuRoute) {
        sendRoute(route)
    }

    override fun resetStatistic(isReset: Boolean) {
        isReset.takeIf { it }
            ?.let { resetStatisticUseCase() }
            .also { updateInfo { copy(showResetAlert = false) } }
    }

    override fun closeFirstLaunchAlert() {
        updateInfo { copy(showFirstLaunchAlert = false) }
    }

    override fun clickSocials(webLink: String) {
        sendRoute(MainMenuRoute.OpenSocials(webLink))
    }

}