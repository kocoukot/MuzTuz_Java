package com.muztus.core.compose.endGameAlert

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.muztus.core.R
import com.muztus.core.compose.AlertButton
import com.muztus.core.compose.AlertButtons
import com.muztus.core.theme.MTTheme
import kotlinx.coroutines.delay


@Composable
fun EndGameLevelDialog(
    coinsAmountText: String,
    starsAmount: Int,
    onActionClick: () -> Unit
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

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.end_level_alert_title),
                            style = MTTheme.typography.informText,
                            fontSize = 24.sp
                        )

                        Row(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(top = 28.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            for ((index, item) in EndGameStars.values().withIndex()) {
                                val image =
                                    if (index < starsAmount) item.openImage else item.closedImage
                                val bottomPadding = if (index == 1) 16.dp else 0.dp

                                val starsAnimation = remember { Animatable(0f) }
                                LaunchedEffect(key1 = Unit) {
                                    delay((500 * index).toLong())
                                    starsAnimation.animateTo(
                                        1f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioHighBouncy,
                                            stiffness = Spring.StiffnessMedium
                                        ),
                                    )
                                }

                                Image(
                                    modifier = Modifier
                                        .scale(starsAnimation.value)
                                        .weight(1f)
                                        .offset(y = -bottomPadding),
                                    painter = painterResource(id = image),
                                    contentDescription = null
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(id = R.drawable.ic_coin),
                                contentDescription = null
                            )

                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = coinsAmountText,
                                style = MTTheme.typography.normalText,
                            )
                        }

                        AlertButton(
                            title = stringResource(id = AlertButtons.OkButtonAlert().positiveButtonTitle),
                            onAction = onActionClick
                        )
                    }
                }
            }
        }
    }
}
