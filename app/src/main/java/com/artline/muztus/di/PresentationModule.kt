package com.artline.muztus.di

import com.artline.muztus.ui.mainMenu.MainMenuViewModel
import com.muztus.game_level_feature.GameLevelViewModel
import com.muztus.level_select_feature.LevelSelectViewModel
import com.muztus.premium_select_feature.PremiaSelectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel { MainMenuViewModel() }

    viewModel { PremiaSelectViewModel() }

    viewModel { (selectedPremium: Int) -> LevelSelectViewModel(selectedPremium) }

    viewModel { (selectedPremium: Int, selectedLevel: Int) ->
        GameLevelViewModel(
            selectedPremium,
            selectedLevel
        )
    }


}