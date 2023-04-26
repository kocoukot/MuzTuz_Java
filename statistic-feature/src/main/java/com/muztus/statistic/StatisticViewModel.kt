package com.muztus.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muztus.domain_layer.model.StatisticModel
import com.muztus.domain_layer.usecase.GetPremiumStateUseCase
import kotlinx.coroutines.launch

class StatisticViewModel(
    private val getStatisticDataUseCase: GetPremiumStateUseCase
) : ViewModel() {

    private val mStatistic: MutableLiveData<StatisticModel> = MutableLiveData()
    val statistic: LiveData<StatisticModel> = mStatistic

    private val mEmptyStata: MutableLiveData<Boolean> = MutableLiveData()
    val emptyStata: LiveData<Boolean> = mEmptyStata

    init {
        viewModelScope.launch {
            getStatisticDataUseCase.invoke().collect { premList ->
                mEmptyStata.postValue(premList.isEmpty())

                if (premList.isNotEmpty()) {
                    mStatistic.postValue(
                        StatisticModel(
                            summaryTime = premList.sumOf { it.levelDuration },
                            levelsPassed = premList.filter { it.isSolved }.size,
                            hintsUsed = premList.filter { it.isSongOpened || it.isAnswerUsed || it.isLettersAmountUsed || it.selectedLetterIndex >= 0 }.size,
                            fastestLevel = premList.minBy { it.levelDuration },
                            longestLevel = premList.maxBy { it.levelDuration }
                        )
                    )
                }
            }
        }
    }
}