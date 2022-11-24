package com.muztus.premium_select_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muztus.core.theme.MTTheme
import com.muztus.premium_select_feature.model.PremiaSelectActions

@Composable
fun PremiaSelectContent(viewModel: PremiaSelectViewModel) {

    val state by viewModel.state.collectAsState()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MTTheme.colors.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MTTheme.colors.background),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.data,
                key = {
                    it.premiumNumber()
                }) { item ->
                item.SetPremiaImage(
                    modifier = Modifier,
                    onSelect = { mocdel ->
                        viewModel.setInputActions(
                            PremiaSelectActions.SelectedPremia(mocdel)
                        )
                    }
                )
            }
        }
    }
}