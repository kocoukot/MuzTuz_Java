package com.artline.muztus.di

import com.artline.muztus.data.repo.GameRepositoryImpl
import com.artline.muztus.domain.repos.GameRepository
import org.koin.dsl.module

val repositoryModule = module {


    factory<GameRepository> { GameRepositoryImpl(get()) }

}