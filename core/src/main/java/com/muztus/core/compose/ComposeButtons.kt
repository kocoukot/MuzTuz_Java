package com.muztus.core.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muztus.core.theme.MTTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameMainButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    buttonText: String,
    onButtonClicked: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()


// Use the state to change the background color
    val buttonColor by animateColorAsState(
        targetValue = if (isPressed) MTTheme.colors.buttonPressed else MTTheme.colors.buttonNotPressed
    )

    val textColor by animateColorAsState(
        targetValue = if (isPressed) MTTheme.colors.buttonPressedText else MTTheme.colors.buttonPressed
    )
    Button(
        onClick = {
            onButtonClicked()
        },
        shape = RoundedCornerShape(4.dp),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
        modifier = modifier,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 0.dp
        ),
    ) {
        Text(
            fontWeight = FontWeight.W600,
            letterSpacing = 2.sp,
            fontSize = 20.sp,
            text = buttonText,
            color = textColor,
            modifier = textModifier
                .padding(vertical = 4.dp, horizontal = 16.dp)
        )
    }
}