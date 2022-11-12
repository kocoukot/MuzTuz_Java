package com.artline.muztus.ui.start

import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.artline.muztus.R
import com.muztus.core.theme.MTTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SplashScreenContent(onPlay: () -> Unit) {

    var visible by remember { mutableStateOf(false) }

    val selected = remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (selected.value) 0.8f else 1f)

    val infiniteTransition = rememberInfiniteTransition()
    val fireAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(key1 = true) {
        delay(100)
        visible = true
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        color = MTTheme.colors.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(initialAlpha = 0.3f) + scaleIn(initialScale = 0.5f),
            ) {
                ScreenImage(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 16.dp),
                    image = R.drawable.img_text_logo
                )
            }

            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                visible = visible,
                enter = fadeIn(animationSpec = tween(600, 0), initialAlpha = 0.3f) +
                        scaleIn(
                            animationSpec = tween(600, 0), initialScale = 0.1f
                        ),
            ) {
                ScreenImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp)
                        .scale(scale)
                        .pointerInteropFilter {
                            when (it.action) {
                                MotionEvent.ACTION_DOWN -> selected.value = true
                                MotionEvent.ACTION_UP -> {
                                    selected.value = false
                                    onPlay()
                                }
                            }
                            true
                        },
                    image = R.drawable.img_play_plate
                )
            }

            ScreenImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(alpha = fireAlpha)
                    .align(Alignment.BottomCenter),
                image = R.drawable.img_fire
            )
        }
    }
}

@Composable
private fun ScreenImage(
    modifier: Modifier = Modifier,
    image: Int
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = image),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )

}