package com.artline.muztus.ui.mainMenu

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.artline.muztus.R
import com.artline.muztus.ui.mainMenu.model.MainMenuActions
import com.muztus.core.compose.GameMainButton
import com.muztus.core.theme.MTTheme
import com.muztus.core.theme.bigSpace

@Composable
fun MainMenuScreenContent(viewModel: MainMenuViewModel) {
    val state = viewModel.state.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MTTheme.colors.background
    ) {

        Column(
            modifier = Modifier
                .padding(top = 44.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GameMainButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .bigSpace(),
                buttonText = stringResource(id = R.string.menu_button_game),
                onButtonClicked = {
                    viewModel.setInputActions(MainMenuActions.MainMenuAction.ClickOnMainGame)
                }
            )

            GameMainButton(
                modifier = Modifier
                    .bigSpace(),
                buttonText = stringResource(id = R.string.menu_button_statistic),
                onButtonClicked = {
                    viewModel.setInputActions(MainMenuActions.MainMenuAction.ClickOnShowStatistic)
                }
            )

            GameMainButton(
                modifier = Modifier
                    .bigSpace(),
                buttonText = stringResource(id = R.string.menu_button_reset),
                onButtonClicked = {
                    viewModel.setInputActions(MainMenuActions.MainMenuAction.ClickOnResetStatistic)
                }
            )

            GameMainButton(
                modifier = Modifier
                    .bigSpace(),
                buttonText = stringResource(id = R.string.menu_button_creators),
                onButtonClicked = {
                    viewModel.setInputActions(MainMenuActions.MainMenuAction.ClickOnShowCreators)
                }
            )

//            GameMainButton(
//                modifier = Modifier
//
//                    .bigSpace(),
//                buttonText = stringResource(id = R.string.menu_button_shop),
//                onButtonClicked = {
//                    viewModel.setInputActions(MainMenuActions.MainMenuAction.ClickOnGoShop)
//                }
//            )
        }
    }
}

