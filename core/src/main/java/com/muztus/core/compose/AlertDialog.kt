package com.muztus.core.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.muztus.core.theme.MTTheme

@Composable
fun AlertDialogComp(
    dialogText: String,
    alertButtonsType: AlertButtons = AlertButtons.YesNoButtonsAlert(),
    onOptionSelected: (Boolean) -> Unit
) {
    MaterialTheme {
        Column {
            var openDialog by remember { mutableStateOf(true) }
            if (openDialog) {
                Dialog(
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false
                    ), onDismissRequest = {
                        openDialog = false
                    }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 26.dp)
                            .background(MTTheme.colors.alertBackground, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = dialogText,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            color = MTTheme.colors.black,
                            fontSize = 16.sp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (alertButtonsType is AlertButtons.YesNoButtonsAlert) {
                                AlertButton(
                                    title = stringResource(alertButtonsType.negativeButtonTitle),
                                    onAction = {
                                        onOptionSelected(false)
                                    })
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                            AlertButton(
                                title = stringResource(id = alertButtonsType.positiveButtonTitle),
                                onAction = {
                                    onOptionSelected(true)
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlertButton(
    title: String,
    onAction: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isSelected by interactionSource.collectIsPressedAsState()
    val buttonColor by animateColorAsState(targetValue = if (isSelected) MTTheme.colors.buttonPressed else MTTheme.colors.mainDarkBrown)
    val textColor by animateColorAsState(targetValue = if (isSelected) MTTheme.colors.background else MTTheme.colors.buttonPressed)

    Button(
        modifier = Modifier,
        interactionSource = interactionSource,
        onClick = onAction,
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)
    ) {
        Text(
            text = title,
            color = textColor,
            fontWeight = FontWeight.W600
        )
    }
}


@Composable
fun LetterSelectAlertDialog(
    correctAnswer: String,
    onLetterSelected: (Int) -> Unit
) {
    MaterialTheme {
        Column {
            var openDialog by remember { mutableStateOf(true) }
            if (openDialog) {
                Dialog(
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false
                    ), onDismissRequest = {
                        openDialog = false
                    }) {

                    Column(
                        modifier = Modifier
                            .background(MTTheme.colors.alertBackground, RoundedCornerShape(8.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            for ((index, letter) in correctAnswer.withIndex()) {
                                if (letter != ' ') {
                                    val interactionSource = remember { MutableInteractionSource() }
                                    val isSelected by interactionSource.collectIsPressedAsState()
                                    val buttonColor by animateColorAsState(targetValue = if (isSelected) MTTheme.colors.buttonPressed else MTTheme.colors.mainDarkBrown)
                                    val textColor by animateColorAsState(targetValue = if (isSelected) MTTheme.colors.background else MTTheme.colors.buttonPressed)

                                    Box(
                                        modifier = Modifier
                                            .padding(horizontal = 2.dp, vertical = 16.dp)
                                            .background(buttonColor)
                                            .width(16.dp)
                                            .drawBehind {
                                                val strokeWidth = 2 * density
                                                val y = size.height + 5 * density
                                                drawLine(
                                                    buttonColor,
                                                    Offset(0f, y),
                                                    Offset(size.width, y),
                                                    strokeWidth
                                                )
                                            }
                                            .clickable(
                                                interactionSource = interactionSource,
                                                indication = null
                                            ) {
                                                onLetterSelected.invoke(index)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .align(Alignment.Center),
                                            fontSize = 24.sp, text = "?",
                                            color = textColor
                                        )
                                    }
                                } else Spacer(modifier = Modifier.width(16.dp))

                            }
                        }
                    }
                }
            }
        }
    }
}
