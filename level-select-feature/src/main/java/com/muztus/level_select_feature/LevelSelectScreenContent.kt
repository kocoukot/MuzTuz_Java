package com.muztus.level_select_feature

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muztus.core.compose.AlertButtons
import com.muztus.core.compose.AlertDialogComp
import com.muztus.core.compose.LetterSelectAlertDialog
import com.muztus.core.compose.MakeToast
import com.muztus.core.compose.endGameAlert.EndGameLevelDialog
import com.muztus.core.ext.castSafe
import com.muztus.core.theme.MTTheme
import com.muztus.level_select_feature.content.PremiumLevelScreenContent
import com.muztus.level_select_feature.content.PremiumScreenContent
import com.muztus.level_select_feature.model.GameToast
import com.muztus.level_select_feature.model.LevelSelectActions
import com.muztus.level_select_feature.model.NextPremiaAlert
import com.muztus.level_select_feature.model.SelectedLevel

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LevelSelectContent(viewModel: LevelSelectViewModel) {

    val state by viewModel.state.collectAsState()
    val selectedPremium = remember { derivedStateOf { state.premiaLevelList } }
    val selectedLevel = remember { derivedStateOf { state.selectedLevel } }

    val coinToast = remember { derivedStateOf { state.coinToast } }

    if (state.nextPremiaOpened == NextPremiaAlert.IsShowing) {
        AlertDialogComp(
            dialogText = "Поздравляем с открытием следующей премии!",
            alertButtonsType = AlertButtons.OkButtonAlert()
        ) {
            viewModel.setInputActions(LevelSelectActions.Base.OnCloseNextPremiaAlert)

        }
    }

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

            when (selectedLevel.value) {
                is SelectedLevel.Empty -> {
                    PremiumScreenContent(selectedPremium.value, viewModel::setInputActions)
                }
            }

            AnimatedVisibility(
                visible = selectedLevel.value is SelectedLevel.SelectedLevelData,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                selectedLevel.value.castSafe<SelectedLevel.SelectedLevelData>()
                    ?.let { selectedLevel ->
                        PremiumLevelScreenContent(
                            selectedLevel,
                            viewModel::setInputActions
                        )
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

