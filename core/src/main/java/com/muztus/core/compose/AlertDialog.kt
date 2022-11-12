package com.muztus.core.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.muztus.core.theme.MTTheme

@Composable
fun AlertDialogComp(
    dialogText: String,
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
                            AlertButton(title = "НЕТ", onAction = {
                                openDialog = false
                                onOptionSelected(false)
                            })
                            Spacer(modifier = Modifier.width(16.dp))
                            AlertButton(title = "ДА", onAction = {
                                openDialog = false
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
private fun AlertButton(
    title: String,
    onAction: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isSelected by interactionSource.collectIsPressedAsState()
    val buttonColor by animateColorAsState(targetValue = if (isSelected) MTTheme.colors.buttonPressed else MTTheme.colors.buttonNotPressed)
    val textColor by animateColorAsState(targetValue = if (isSelected) MTTheme.colors.background else MTTheme.colors.buttonPressed)

    Button(
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
