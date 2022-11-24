package com.artline.muztus.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muztus.domain_layer.model.GameMainInfo
import com.muztus.domain_layer.usecase.GetGameCoinsUseCase
import com.muztus.domain_layer.usecase.SetCoinsAmountUseCase

class MainViewModel(
    private val getGameCoinsUseCase: GetGameCoinsUseCase,
    private val setCoinsAmountUseCase: SetCoinsAmountUseCase
) : ViewModel() {

    private val mCoins: MutableLiveData<GameMainInfo> = MutableLiveData(getGameCoinsUseCase())
    val coins: LiveData<GameMainInfo>
        get() = mCoins

    init {

    }


    fun addCoins() {
        setCoinsAmountUseCase(100) //todo remove after test
        updateCoins()
    }

    fun updateCoins() {
        mCoins.postValue(getGameCoinsUseCase.invoke())
    }
}
