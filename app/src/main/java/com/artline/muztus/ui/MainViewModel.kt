package com.artline.muztus.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muztus.domain_layer.usecase.GetGameCoinsUseCase
import com.muztus.domain_layer.usecase.SetCoinsAmountUseCase

class MainViewModel(
    private val getGameCoinsUseCase: GetGameCoinsUseCase,
    private val setCoinsAmountUseCase: SetCoinsAmountUseCase
) : ViewModel() {

    private val mCoins: MutableLiveData<Int> = MutableLiveData(getGameCoinsUseCase())
    val coins: LiveData<Int>
        get() = mCoins

    init {
//        viewModelScope.launch {
//            getGameCoinsUseCase().observe()
//            kotlin.runCatching { getGameCoinsUseCase() }
//                .onSuccess {
//                    mCoins.postValue(it.value)
//                }
//                .onFailure {
//                    println("getGameCoinsUseCase $it")
//                }
//        }
    }

    fun addCoins() {
        setCoinsAmountUseCase(100)
        updateCoins()
    }

    fun updateCoins() {
        mCoins.postValue(getGameCoinsUseCase.invoke())
    }
}
