package com.muztus.level_select_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muztus.core.theme.MTTheme
import com.muztus.level_select_feature.model.LevelSelectActions

@Composable
fun LevelSelectContent(viewModel: LevelSelectViewModel) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        backgroundColor = MTTheme.colors.background
    ) { _ ->


        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 3),
            modifier = Modifier
                .background(MTTheme.colors.background)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.data, key = { item -> item.levelIndex() }) { item ->
                item.LevelImage(
                    modifier = Modifier,
                    onSelect = { index ->
                        viewModel.setInputActions(LevelSelectActions.SelectLevel(index))
                    })

            }

        }
    }
}