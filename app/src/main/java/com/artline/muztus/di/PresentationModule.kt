package com.artline.muztus.di

import androidx.room.Room
import com.artline.muztus.data.repo.GameRepositoryImpl
import com.artline.muztus.ui.MainViewModel
import com.artline.muztus.ui.mainMenu.MainMenuViewModel
import com.artline.muztus.ui.tutorial.TutorialViewModel
import com.muztus.database.AppDatabase
import com.muztus.domain_layer.repos.GameRepository
import com.muztus.domain_layer.usecase.GetGameCoinsUseCase
import com.muztus.domain_layer.usecase.GetPremiumDataUseCase
import com.muztus.domain_layer.usecase.GetPremiumStateUseCase
import com.muztus.domain_layer.usecase.SetCoinsAmountUseCase
import com.muztus.domain_layer.usecase.global.GetSoundsStateUseCase
import com.muztus.domain_layer.usecase.global.IsFirstLaunchUseCase
import com.muztus.domain_layer.usecase.global.ResetStatisticUseCase
import com.muztus.domain_layer.usecase.global.SetSoundsStateUseCase
import com.muztus.domain_layer.usecase.level.GetLevelInfoUseCase
import com.muztus.domain_layer.usecase.level.SetLevelInfoUseCase
import com.muztus.level_select_feature.LevelSelectViewModel
import com.muztus.premium_select_feature.PremiaSelectViewModel
import com.muztus.statistic.StatisticViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val authModule = module {

    single { Room.databaseBuilder(get(), AppDatabase::class.java, "room_article.db").build() }

    single { get<AppDatabase>().gameLevelInfo() }

    viewModel { MainMenuViewModel(get(), get()) }

    viewModel { PremiaSelectViewModel(get()) }

    viewModel { (selectedPremium: Int) ->
        LevelSelectViewModel(
            selectedPremium,
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    viewModel { MainViewModel(get(), get(), get(), get()) }

    viewModel { StatisticViewModel(get()) }

    viewModel { TutorialViewModel(get()) }


}


val domainModule = module {
    factory { GetGameCoinsUseCase(get()) }

    factory { SetCoinsAmountUseCase(get()) }

    factory { GetLevelInfoUseCase(get()) }

    factory { GetPremiumDataUseCase(get()) }

    factory { ResetStatisticUseCase(get()) }

    factory { IsFirstLaunchUseCase(get()) }

    factory { GetSoundsStateUseCase(get()) }

    factory { SetSoundsStateUseCase(get()) }

    factory { SetLevelInfoUseCase(get()) }

    factory { GetPremiumStateUseCase(get()) }

}


val repositoryModule = module {

    factory<GameRepository> { GameRepositoryImpl(get(), get()) }

}