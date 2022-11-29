package com.artline.muztus.di

import com.artline.muztus.data.repo.GameRepositoryImpl
import com.artline.muztus.ui.MainViewModel
import com.artline.muztus.ui.mainMenu.MainMenuViewModel
import com.muztus.domain_layer.repos.GameRepository
import com.muztus.domain_layer.usecase.*
import com.muztus.level_select_feature.LevelSelectViewModel
import com.muztus.premium_select_feature.PremiaSelectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val authModule = module {
    viewModel { MainMenuViewModel(get(), get()) }

    viewModel { PremiaSelectViewModel() }

    viewModel { (selectedPremium: Int) ->
        LevelSelectViewModel(
            selectedPremium,
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel { MainViewModel(get(), get()) }
}


val domainModule = module {
    factory { GetGameCoinsUseCase(get()) }

    factory { SetCoinsAmountUseCase(get()) }

    factory { GetLevelInfoUseCase(get()) }

    factory { GetPremiumDataUseCase(get()) }

    factory { ResetStatisticUseCase(get()) }

    factory { IsFirstLaunchUseCase(get()) }

}


val repositoryModule = module {

    factory<GameRepository> { GameRepositoryImpl(get()) }

}