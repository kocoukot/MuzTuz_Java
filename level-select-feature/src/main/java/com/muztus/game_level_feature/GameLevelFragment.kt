package com.muztus.game_level_feature

import androidx.compose.runtime.Composable
import com.muztus.core.ext.SupportInfoBar
import com.muztus.core.ext.requireArg
import com.muztus.core_mvi.BaseFragment
import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.UpdateCoins
import com.muztus.game_level_feature.model.GameLevelRoute
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GameLevelFragment : BaseFragment.BaseF<GameLevelViewModel>(), SupportInfoBar {
    override val viewModel: GameLevelViewModel by viewModel {
        parametersOf(
            selectedPremium,
            selectedLevel
        )
    }
    private val selectedPremium by requireArg<Int>(ARG_PREMIUM_INDEX)
    private val selectedLevel by requireArg<Int>(ARG_LEVEL_INDEX)

    override var screenContent: (@Composable (GameLevelViewModel) -> Unit)? =
        { GameLevelScreenContent(viewModel) }

    override fun observeData(composeRoute: ((ComposeFragmentRoute) -> Unit)?) {
        super.observeData { route ->
            when (route) {
                GameLevelRoute.UpdateCoins -> (requireActivity() as UpdateCoins).updateCoins()
            }
        }
    }

    companion object {
        private const val ARG_PREMIUM_INDEX = "premium_index"
        private const val ARG_LEVEL_INDEX = "level_index"
    }
}