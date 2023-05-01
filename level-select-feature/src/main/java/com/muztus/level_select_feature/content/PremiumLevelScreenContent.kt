package com.muztus.level_select_feature.content

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muztus.core.compose.AlertDialogComp
import com.muztus.core.theme.MTTheme
import com.muztus.level_select_feature.R
import com.muztus.level_select_feature.model.LevelSelectActions
import com.muztus.level_select_feature.model.SelectedLevel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PremiumLevelScreenContent(
    data: SelectedLevel.SelectedLevelData,
    onAction: (LevelSelectActions.Base) -> Unit
) {
    val boxSelection = remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (boxSelection.value) 0.8f else 1f)


    if (data.showHintAlert && data.selectedHint != null) {
        AlertDialogComp(dialogText = stringResource(
            id = R.string.user_hint_alert_text,
            stringResource(id = data.selectedHint.hintName()),
            data.selectedHint.hintCost()
        ), onOptionSelected = {
            onAction.invoke(LevelSelectActions.Base.OnHintAlertDecision(it))
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .background(MTTheme.colors.background)
    ) {


        /**
         * song hint
         */
        data.selectedLevelModel.GetLevelSongHint(
            Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp)
        )


        /**
         * Free coins button
         */

        if (!data.selectedLevelModel.isSolved()) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.TopEnd)
                    .padding(horizontal = 16.dp),
            ) {
                Image(
                    modifier = Modifier
                        .scale(scale)
                        .pointerInteropFilter {
                            when (it.action) {
                                MotionEvent.ACTION_DOWN -> boxSelection.value = true
                                MotionEvent.ACTION_UP -> {
                                    boxSelection.value = false
                                    onAction.invoke(LevelSelectActions.Base.OnFreeCoins)
                                }
                            }
                            true
                        },
                    painter = painterResource(id = R.drawable.img_free_coin_chest),
                    contentDescription = null
                )
            }
        }

        /**
         * Level UI
         */

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /** Level Image*/
            data.selectedLevelModel.GetLevelImage(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            )


            /**  Letters amount hint used */
            data.selectedLevelModel.GetLettersAmount(modifier = Modifier)

            BottomBarContent(data.selectedLevelModel, onAction::invoke)
        }
    }
}