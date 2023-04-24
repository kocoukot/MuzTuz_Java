package com.muztus.core.compose

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muztus.core.theme.MTTheme

interface ToastShower {
    fun showToast(context: Context, text: String)

    class Base : ToastShower {
        override fun showToast(context: Context, text: String) {
            Toast.makeText(
                context,
                "Для начала нужно ввести хоть какой-то ответ!",
                Toast.LENGTH_SHORT
            ).apply {
                setGravity(Gravity.CENTER, 0, 350)
            }.show()
        }

    }
}

@Composable
fun MakeToast(modifier: Modifier = Modifier, state: SnackbarHostState) {
    SnackbarHost(
        modifier = modifier.padding(horizontal = 16.dp),
        hostState = state,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(bottom = 50.dp),
                content = {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                        text = data.message,
                        color = MTTheme.colors.white,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    )
}