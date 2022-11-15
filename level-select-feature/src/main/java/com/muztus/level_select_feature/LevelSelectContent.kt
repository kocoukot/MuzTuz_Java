package com.muztus.level_select_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Surface
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
    Surface(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MTTheme.colors.background)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 3),
            modifier = Modifier
                .background(MTTheme.colors.background)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(state.data, key = { it.levelIndex() }) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .heightIn(max = 120.dp)
//                        .clickable(
//                            interactionSource = remember { MutableInteractionSource() },
//                            indication = null
//                        ) {
////                            onSelect.invoke(LevelSelectActions.SelectLevel(levelIndex))
//                        },
//                    contentAlignment = Alignment.Center
//                ) {
//
//                    Image(
//                        alignment = Alignment.Center,
//
//                        modifier = Modifier
//                            .fillMaxHeight(),
//                        painter = painterResource(id = it.levelInfo().levelImage),
//                        contentDescription = null
//                    )
//                    if (it.levelInfo().isLevelPassed)
//                        Image(
//                            alignment = Alignment.Center,
//                            modifier = Modifier
//                                .fillMaxSize(),
//                            contentScale = ContentScale.FillWidth,
//                            painter = painterResource(id = R.drawable.level_solved),
//                            contentDescription = null
//                        )
//                }

                it.LevelImage(
                    modifier = Modifier,
                    onSelect = { index ->
                        viewModel.setInputActions(LevelSelectActions.SelectLevel(index))
                    })
            }
        }
    }

}