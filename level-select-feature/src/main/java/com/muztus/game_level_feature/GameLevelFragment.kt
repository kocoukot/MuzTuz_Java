package com.muztus.game_level_feature

import androidx.compose.runtime.Composable
import com.muztus.core.ext.SupportInfoBar
import com.muztus.core_mvi.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GameLevelFragment : BaseFragment.BaseF<GameLevelViewModel>(), SupportInfoBar {
    override val viewModel: GameLevelViewModel by viewModel {
        parametersOf(
            selectedPremium,
            selectedLevel
        )
    }
    private val selectedPremium = 2// by requireArg<Int>(ARG_PREMIUM_INDEX)
    private val selectedLevel = 5// by requireArg<Int>(ARG_LEVEL_INDEX)

    override var screenContent: (@Composable (GameLevelViewModel) -> Unit)? =
        { GameLevelScreenContent(viewModel) }


    companion object {
        private const val ARG_PREMIUM_INDEX = "premium_index"
        private const val ARG_LEVEL_INDEX = "level_index"

    }

}