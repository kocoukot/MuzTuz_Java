package com.muztus.level_select_feature.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muztus.core.theme.MTTheme
import com.muztus.domain_layer.model.PremiaLevelModel
import com.muztus.level_select_feature.model.LevelSelectActions

@Composable
fun PremiumScreenContent(
    data: List<PremiaLevelModel>,
    onPremiumClick: (LevelSelectActions.Base) -> Unit
) {

    val action = remember<(Int) -> Unit> {
        { index ->
            onPremiumClick.invoke(LevelSelectActions.Base.SelectLevel(index))
        }
    }


    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        modifier = Modifier
            .background(MTTheme.colors.background)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(data, key = { item -> item.levelIndex() }) { item ->
            item.LevelImage(
                modifier = Modifier,
                onSelect = action::invoke
            )
            println("item $item ${item.levelInfo()}")
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}