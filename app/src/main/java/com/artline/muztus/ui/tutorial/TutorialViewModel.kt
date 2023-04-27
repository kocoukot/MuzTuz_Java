package com.artline.muztus.ui.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muztus.database.LevelInfoEntity
import com.muztus.domain_layer.usecase.level.SetLevelInfoUseCase
import kotlinx.coroutines.launch
import java.util.Locale

class TutorialViewModel(
    private val setLevelInfoUseCase: SetLevelInfoUseCase

) : ViewModel() {

    private val mIsAnswerCorrect: MutableLiveData<Boolean> = MutableLiveData()
    val isAnswerCorrect: LiveData<Boolean> = mIsAnswerCorrect

    fun checkAnswer(answer: String) {
        mIsAnswerCorrect.value =
            answer.trim().lowercase(Locale.getDefault()).equals(CORRECT_TUTORIAL, true).also {
                if (it) {
                    viewModelScope.launch {
                        setLevelInfoUseCase.invoke(
                            LevelInfoEntity(
                                premiaIndex = 0,
                                levelIndex = 0,
                                isSolved = true,
                                levelDuration = 0,
                                isLettersAmountUsed = false,
                                selectedLetterIndex = -1,
                                isSongOpened = false,
                                isAnswerUsed = false,
                            )
                        )
                    }
                }
            }
    }


    companion object {
        private const val CORRECT_TUTORIAL = "мумий тролль"
    }
}