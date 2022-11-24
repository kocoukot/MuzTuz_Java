package com.artline.muztus.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.artline.muztus.data.SharedPreferencesStorage
import org.koin.dsl.module

private const val PREFERENCES_FILE_NAME = "digi.prefs"
private val key = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

val storageModule = module {


    single {
        kotlin.runCatching {
            EncryptedSharedPreferences.create(
                PREFERENCES_FILE_NAME,
                key,
                get(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }.getOrDefault(
            get<Context>().getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        )
    }

    single { SharedPreferencesStorage(get()) }

}