package com.artline.muztus.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muztus.domain_layer.model.GameSoundsInfo
import com.muztus.domain_layer.model.GameStatsInfo
import com.muztus.domain_layer.model.IGameSound
import com.muztus.domain_layer.usecase.GetGameCoinsUseCase
import com.muztus.domain_layer.usecase.SetCoinsAmountUseCase
import com.muztus.domain_layer.usecase.global.GetSoundsStateUseCase
import com.muztus.domain_layer.usecase.global.SetSoundsStateUseCase

class MainViewModel(
    private val getGameCoinsUseCase: GetGameCoinsUseCase,
    private val setCoinsAmountUseCase: SetCoinsAmountUseCase,
    getSoundsStateUseCase: GetSoundsStateUseCase,
    private val setSoundsStateUseCase: SetSoundsStateUseCase,
) : ViewModel() {

    private val mCoins: MutableLiveData<GameStatsInfo> = MutableLiveData(getGameCoinsUseCase())
    val coins: LiveData<GameStatsInfo>
        get() = mCoins

    private val mSounds: MutableLiveData<GameSoundsInfo> = MutableLiveData(getSoundsStateUseCase())
    val sounds: LiveData<GameSoundsInfo>
        get() = mSounds


    fun addCoins() {
        setCoinsAmountUseCase(100) //todo remove after test
        updateCoins()
    }

    fun updateCoins() {
        mCoins.postValue(getGameCoinsUseCase.invoke())
    }

    fun soundChange(soundType: IGameSound) {
        mSounds.value?.let {
            println("sound VM ${it.soundState.soundState()}")
            it.changeSound(soundType)
            println("sound VM ${it.soundState.soundState()}")
            setSoundsStateUseCase.invoke(it)
            mSounds.value = it
        }
    }
}
