package com.muztus.shop_feature

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muztus.core.theme.MTTheme
import com.muztus.shop_feature.data.ShopActions
import com.muztus.shop_feature.model.ShopItem

@Composable
fun ShopScreenContent(viewModel: ShopViewModel) {

    val state by viewModel.state.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MTTheme.colors.background) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                )
        ) {
            items(state.productList, key = { item -> item.productId }) { item ->
                ShopButton(item) {
                    viewModel.setInputActions(ShopActions.Base.OnShopItemSelect(item))
                }
            }
        }

    }
}

@Composable
fun ShopButton(shopItem: ShopItem, onItemSelect: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isSelected by interactionSource.collectIsPressedAsState()
    val buttonColor by animateColorAsState(targetValue = if (isSelected) MTTheme.colors.buttonPressed else MTTheme.colors.mainDarkBrown)
    val textColor by animateColorAsState(targetValue = if (isSelected) MTTheme.colors.background else MTTheme.colors.buttonPressed)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 8.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onItemSelect.invoke()
            },
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(4.dp))
                .drawBehind {
                    drawRect(color = buttonColor)
                }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(width = 130.dp, height = 100.dp),
                painter = painterResource(id = shopItem.imgRes),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Text(
                text = "x ${shopItem.description}",
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp),
                fontSize = 28.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 8.dp, end = 8.dp),
                contentAlignment = Alignment.BottomCenter
            ) {

                Text(
                    text = shopItem.formattedPrice,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MTTheme.colors.buttonPressed)
                        .padding(vertical = 4.dp, horizontal = 10.dp),
                    fontSize = 16.sp,
                    color = MTTheme.colors.mainDarkBrown,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }


    }
}
//
//
//@Preview(showBackground = true)
//@Composable
//fun SHopBtnPrev2() {
//    ShopButton(2)
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SHopBtnPrev4() {
//    ShopButton(4)
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SHopBtnPrev1() {
//    ShopButton(1)
//}