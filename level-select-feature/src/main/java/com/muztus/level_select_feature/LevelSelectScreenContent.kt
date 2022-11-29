package com.muztus.level_select_feature

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muztus.core.compose.LetterSelectAlertDialog
import com.muztus.core.compose.endGameAlert.EndGameLevelDialog
import com.muztus.core.theme.MTTheme
import com.muztus.level_select_feature.content.PremiumLevelScreenContent
import com.muztus.level_select_feature.content.PremiumScreenContent
import com.muztus.level_select_feature.model.GameToast
import com.muztus.level_select_feature.model.LevelSelectActions
import com.muztus.level_select_feature.model.SelectedLevel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LevelSelectContent(viewModel: LevelSelectViewModel) {

    val state by viewModel.state.collectAsState()
    val selectedPremium by remember { mutableStateOf(state.premiaLevelList) }
    val coinToast = remember { derivedStateOf { state.coinToast } }

    val scaffoldState = rememberScaffoldState()


    BackHandler {
        viewModel.setInputActions(LevelSelectActions.Base.OnBackPressed)
    }


    if (state.showCompleteLevelAlert) {
        EndGameLevelDialog(coinsAmountText = state.coinsAmountWin,
            starsAmount = if (state.levelStarts < 0) 0 else state.levelStarts,
            onActionClick = {
                viewModel.setInputActions(LevelSelectActions.Base.CloseEndGameAlert)
            })
    }


    state.showLetterAlert.takeIf { it.isNotEmpty() }?.let { answer ->
        LetterSelectAlertDialog(answer) {
            if (it >= 0) viewModel.setInputActions(LevelSelectActions.Base.UseOneLetterHint(it))
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        backgroundColor = MTTheme.colors.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {


            Crossfade(targetState = state.selectedLevel) { targetState ->
                when (targetState) {
                    is SelectedLevel.Empty -> {
                        PremiumScreenContent(selectedPremium, viewModel::setInputActions)
                    }

                    is SelectedLevel.SelectedLevelData -> {
                        PremiumLevelScreenContent(targetState, viewModel::setInputActions)
                    }
                }
            }

            when (coinToast.value) {
                GameToast.Empty -> {}

                else -> {
                    val msgText = coinToast.value.toastText()
                    LaunchedEffect(key1 = Unit) {
                        scaffoldState.snackbarHostState.showSnackbar(msgText)
                        viewModel.setInputActions(LevelSelectActions.Base.ClearToastCoins)
                    }
                }
            }

            MakeToast(
                modifier = Modifier.align(Alignment.BottomCenter),
                state = scaffoldState.snackbarHostState
            )
        }
    }
}

@Composable
fun MakeToast(modifier: Modifier = Modifier, state: SnackbarHostState) {

    SnackbarHost(
        modifier = modifier.padding(horizontal = 16.dp),
        hostState = state,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                        text = data.message,
                        color = MTTheme.colors.white,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    )
}

