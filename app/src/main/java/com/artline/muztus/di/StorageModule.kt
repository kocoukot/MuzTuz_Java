package com.artline.muztus.di

import com.artline.muztus.data.SharedPreferencesStorage
import org.koin.dsl.module

val storageModule = module {


    single { SharedPreferencesStorage(get()) }

}