package com.muztus.level_select_feature.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muztus.core.compose.AlertDialogComp
import com.muztus.core.theme.MTTheme
import com.muztus.domain_layer.model.HintModel
import com.muztus.level_select_feature.R
import com.muztus.level_select_feature.model.LevelSelectActions
import com.muztus.level_select_feature.model.SelectedLevel

@Composable
fun PremiumLevelScreenContent(
    data: SelectedLevel.SelectedLevelData,
    onAction: (LevelSelectActions.Base) -> Unit
) {
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
            .background(MTTheme.colors.background)
    ) {

        data.selectedLevelModel.hintsRow().filterIsInstance<HintModel.SongHint>().first()
            .takeIf { it.isEnabled() }?.let {
                Text(
                    color = MTTheme.colors.white,
                    fontSize = 12.sp,
                    text = data.selectedLevelModel.getLevelSongHint(),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 16.dp)
                )
            }

        Box(
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.TopEnd)
                .padding(horizontal = 16.dp)
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.img_free_coin_chest),
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(id = data.selectedLevelModel.getLevelImage()),
                    modifier = Modifier,
                    contentDescription = null,
                )
            }

            data.selectedLevelModel.getLettersAmount().takeIf { it.isNotEmpty() }
                ?.let { hintString ->

                    Text(
                        color = MTTheme.colors.buttonPressed,
                        fontSize = 28.sp,
                        text = if (data.selectedLevelModel.isSolved()) data.selectedLevelModel.getCorrectAnswer()
                            .split(" ")
                            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } } else hintString,
                        modifier = Modifier
                    )
                }
            BottomBarContent(data.selectedLevelModel, onAction::invoke)
        }


    }
}