package com.muztus.level_select_feature

import androidx.compose.runtime.Composable
import com.muztus.core.ext.SupportInfoBar
import com.muztus.core_mvi.BaseFragment
import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.UpdateCoins
import com.muztus.level_select_feature.model.LevelSelectRoute
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LevelSelectFragment : BaseFragment.BaseF<LevelSelectViewModel>(), SupportInfoBar {
    override val viewModel: LevelSelectViewModel by viewModel {
        parametersOf(selectedPremium)
    }
    private val selectedPremium = 3// by requireArg<Int>(ARG_PREMIUM_INDEX) //todo fix later

    override var screenContent: (@Composable (LevelSelectViewModel) -> Unit)? =
        { LevelSelectContent(viewModel) }

    override fun observeData(composeRoute: ((ComposeFragmentRoute) -> Unit)?) {
        super.observeData { route ->
            when (route) {
                LevelSelectRoute.UpdateCoins -> (requireActivity() as UpdateCoins).updateCoins()
            }
        }
    }

    companion object {
        private const val ARG_PREMIUM_INDEX = "premium_index"
    }

}