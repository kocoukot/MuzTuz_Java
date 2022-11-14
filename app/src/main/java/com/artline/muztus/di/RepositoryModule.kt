package com.artline.muztus.di

import com.artline.muztus.data.repo.GameRepositoryImpl
import com.muztus.domain_layer.repos.GameRepository
import org.koin.dsl.module

val repositoryModule = module {


    factory<GameRepository> { GameRepositoryImpl(get()) }

}