package com.muztus.game_level_feature.content

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.muztus.core.ext.Keyboard
import com.muztus.core.ext.keyboardAsState
import com.muztus.core.ext.letters
import com.muztus.core.theme.MTTheme
import com.muztus.game_level_feature.data.GameLevelModel
import com.muztus.game_level_feature.model.GameLevelAction
import com.muztus.level_select_feature.R

@Composable
fun BottomBarContent(
    data: GameLevelModel,
    bottomBarActions: (GameLevelAction) -> Unit
) {
    val isKeyboardOpen by keyboardAsState()
    val levelImageScale by animateFloatAsState(targetValue = if (isKeyboardOpen == Keyboard.Opened) 0.8f else 1f)
    val hintPadding by animateDpAsState(targetValue = if (isKeyboardOpen == Keyboard.Opened) 0.dp else 8.dp)

    LevelInput(modifier = Modifier) { bottomBarActions.invoke(GameLevelAction.CheckUSerInput(it)) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = hintPadding),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        for (hint in data.hintsRow()) {
            hint.HintImage(modifier = Modifier
                .scale(levelImageScale)
                .weight(1f)
                .clickable {

                })
        }
    }
}

@Composable
fun LevelInput(
    modifier: Modifier,
    onAction: (String) -> Unit
) {
    var input by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonColor by animateColorAsState(
        targetValue = if (isPressed) MTTheme.colors.buttonPressed else MTTheme.colors.buttonNotPressed
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
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp, top = 0.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MTTheme.colors.black,
                backgroundColor = Color.Transparent,
                cursorColor = MTTheme.colors.background,
                focusedIndicatorColor = MTTheme.colors.buttonNotPressed,
                unfocusedIndicatorColor = MTTheme.colors.alertBackground,
            ),
            value = input,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            onValueChange = {
                input = it.letters()
            },
            placeholder = {
                if (input.isEmpty())
                    Text(
                        stringResource(id = R.string.input_hint),
                        modifier = Modifier,
                    )

            })

        Button(
            contentPadding = PaddingValues(6.dp),
            interactionSource = interactionSource,
            enabled = input.isNotEmpty(),
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
