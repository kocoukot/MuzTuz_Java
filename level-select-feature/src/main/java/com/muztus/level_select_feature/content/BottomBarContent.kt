package com.muztus.level_select_feature.content

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muztus.core.ext.Keyboard
import com.muztus.core.ext.keyboardAsState
import com.muztus.core.theme.MTTheme
import com.muztus.domain_layer.model.GameLevelModel
import com.muztus.level_select_feature.R
import com.muztus.level_select_feature.model.LevelSelectActions

@Composable
fun BottomBarContent(
    data: GameLevelModel,
    bottomBarActions: (LevelSelectActions.Base) -> Unit
) {
    val isKeyboardOpen by keyboardAsState()
    val hintPadding by animateDpAsState(targetValue = if (isKeyboardOpen == Keyboard.Opened) 0.dp else 8.dp)


    /** Level answer input */
    LevelInput(modifier = Modifier.padding(horizontal = 16.dp), isSolved = data.isSolved()) {
        bottomBarActions.invoke(
            LevelSelectActions.Base.CheckUSerInput(
                it
            )
        )
    }

    /** Level hints row */

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = hintPadding),
        verticalAlignment = Alignment.Bottom
    ) {
        for (hint in data.hintsRow()) {
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val levelImageScale by animateFloatAsState(
                targetValue =
                if (isKeyboardOpen == Keyboard.Opened && isPressed) 0.6f
                else if (isKeyboardOpen == Keyboard.Opened || isPressed) 0.8f
                else 1f
            )
            hint.HintImage(modifier = Modifier
                .weight(1f)
                .scale(levelImageScale)
                .clip(CircleShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    bottomBarActions.invoke(LevelSelectActions.Base.OnUserTapHint(hint))
                }
            )
        }
    }
}

@Composable
fun LevelInput(
    modifier: Modifier,
    isSolved: Boolean,
    onAction: (String) -> Unit
) {
    var input by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonColor by animateColorAsState(
        targetValue = if (isPressed) MTTheme.colors.buttonPressed else MTTheme.colors.mainDarkBrown
    )

    val checkMarkColor by animateColorAsState(
        targetValue = when {
            input.isEmpty() -> MTTheme.colors.white
            isPressed -> MTTheme.colors.buttonPressedText
            else -> MTTheme.colors.buttonPressed
        }
    )

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        TextField(
            textStyle = TextStyle.Default.copy(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.optima_bold))
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp, top = 0.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MTTheme.colors.black,
                backgroundColor = Color.Transparent,
                cursorColor = MTTheme.colors.mainDarkBrown,
                focusedIndicatorColor = MTTheme.colors.mainDarkBrown,
                unfocusedIndicatorColor = MTTheme.colors.alertBackground,
            ),
            enabled = isSolved.not(),
            value = input,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            ),
            onValueChange = {
                input = it
            },
            placeholder = {
                if (input.isEmpty())
                    Text(
                        stringResource(id = R.string.input_hint),
                        modifier = Modifier,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.optima_bold))
                    )

            })

        Button(
            contentPadding = PaddingValues(6.dp),
            interactionSource = interactionSource,
            enabled = if (isSolved) false else input.isNotEmpty(),
            onClick = { onAction.invoke(input) },
            modifier = Modifier.size(width = 48.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor,
                disabledBackgroundColor = MTTheme.colors.buttonDisabled
            )
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                tint = checkMarkColor,
                painter = painterResource(id = R.drawable.ic_checkmark),
                contentDescription = null
            )
        }
    }
}
