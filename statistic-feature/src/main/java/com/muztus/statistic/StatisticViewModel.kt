package com.muztus.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muztus.database.LevelInfoEntity
import com.muztus.domain_layer.model.StatisticModel
import com.muztus.domain_layer.usecase.GetPremiumStateUseCase
import kotlinx.coroutines.launch

class StatisticViewModel(
    private val getStatisticDataUseCase: GetPremiumStateUseCase
) : ViewModel() {

    private val mStatistic: MutableLiveData<StatisticModel> = MutableLiveData()
    val statistic: LiveData<StatisticModel> = mStatistic

    init {
        viewModelScope.launch {
            getStatisticDataUseCase.invoke().collect { premList ->
                mStatistic.postValue(
                    StatisticModel(
                        summaryTime = 0,
                        levelsPassed = premList.filter { it.isSolved }.size,
                        hintsUsed = 0,
                        fastestLevel = LevelInfoEntity(
                            premiaIndex = 0,
                            levelIndex = 0,
                            isSolved = false
                        ),
                        longestLevel = LevelInfoEntity(
                            premiaIndex = 0,
                            levelIndex = 0,
                            isSolved = false
                        )
                    )
                )
            }
        }
    }
}