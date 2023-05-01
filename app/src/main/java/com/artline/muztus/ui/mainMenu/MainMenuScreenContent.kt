package com.artline.muztus.ui.mainMenu

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.artline.muztus.R
import com.artline.muztus.ui.mainMenu.model.MainMenuActions
import com.artline.muztus.ui.splash.ScreenImage
import com.muztus.core.compose.AlertButtons
import com.muztus.core.compose.AlertDialogComp
import com.muztus.core.compose.GameMainButton
import com.muztus.core.theme.MTTheme
import com.muztus.core.theme.bigSpace

@Composable
fun MainMenuScreenContent(viewModel: MainMenuViewModel) {
    val state by viewModel.state.collectAsState()

    if (state.showResetAlert) {
        AlertDialogComp(
            dialogText = stringResource(id = R.string.menu_button_reset_alert_title),
            onOptionSelected = {
                viewModel.setInputActions(MainMenuActions.MainMenuAction.OnResetStatisticDecision(it))
            }
        )
    }

    if (state.showFirstLaunchAlert) {
        AlertDialogComp(
            dialogText = stringResource(id = R.string.menu_first_launch_alert_text),
            alertButtonsType = AlertButtons.OkButtonAlert(),
            onOptionSelected = {
                viewModel.setInputActions(MainMenuActions.MainMenuAction.CloseFirstLaunchAlert)
            }
        )
    }
    val infiniteTransition = rememberInfiniteTransition()
    val fireAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        )
    )


    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MTTheme.colors.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(36.dp))

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
                        viewModel.setInputActions(MainMenuActions.MainMenuAction.ClickOnGoStatistic)
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

                GameMainButton(
                    modifier = Modifier

                        .bigSpace(),
                    buttonText = stringResource(id = R.string.menu_button_shop),
                    onButtonClicked = {
                        viewModel.setInputActions(MainMenuActions.MainMenuAction.ClickOnGoShop)
                    }
                )

            }
            Spacer(modifier = Modifier.weight(1f))

            ScreenImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(alpha = fireAlpha),
                image = R.drawable.img_fire
            )
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(220.dp)
//                    .padding(horizontal = 16.dp)
//                    .padding(bottom = 16.dp),
//                verticalAlignment = Alignment.Bottom
//            ) {
//
//                Image(
//                    modifier = Modifier,
//                    painter = painterResource(id = R.drawable.img_guy_review_say),
//                    contentDescription = "contacts place"
//                )
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(top = 12.dp, bottom = 8.dp)
//                        .background(MTTheme.colors.alertBackground, RoundedCornerShape(8.dp)),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//
//                    Text(
//                        text = stringResource(R.string.main_menu_receive_review_title),
//                        style = MTTheme.typography.informText,
//                        modifier = Modifier
//                            .padding(horizontal = 2.dp)
//                            .padding(top = 8.dp),
//                        textAlign = TextAlign.Center,
//                    )
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .fillMaxHeight(),
//                        horizontalArrangement = Arrangement.SpaceAround,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        for (item in SocialMediaTypes.values()) {
//                            val interactionSource = remember { MutableInteractionSource() }
//                            val buttonColor by animateColorAsState(targetValue = if (interactionSource.collectIsPressedAsState().value) MTTheme.colors.buttonPressed else MTTheme.colors.mainDarkBrown)
//
//                            val onSocialSelect = remember<(String) -> Unit> {
//                                {
//                                    viewModel.setInputActions(
//                                        MainMenuActions.MainMenuAction.OnSocialSelect(it)
//                                    )
//                                }
//
//                            }
//                            IconButton(
//                                modifier = Modifier
//                                    .shadow(4.dp, CircleShape)
//                                    .size(64.dp),
//                                interactionSource = interactionSource,
//                                onClick = {
//                                    onSocialSelect.invoke(item.webLink)
//                                },
//                            ) {
//                                Icon(
//                                    painter = painterResource(id = item.image),
//                                    contentDescription = "social contacts",
//                                    tint = buttonColor
//                                )
//                            }
//                        }
//                    }
//                }
//            }
        }
    }
}

