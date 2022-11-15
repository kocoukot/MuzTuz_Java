package com.muztus.level_select_feature

import androidx.compose.runtime.Composable
import com.muztus.core.ext.SupportInfoBar
import com.muztus.core_mvi.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LevelSelectFragment : BaseFragment.BaseF<LevelSelectViewModel>(), SupportInfoBar {
    override val viewModel: LevelSelectViewModel by viewModel {
        parametersOf(selectedPremium)
    }
    private val selectedPremium = 1// by requireArg<Int>(ARG_PREMIUM_INDEX)

    override var screenContent: (@Composable (LevelSelectViewModel) -> Unit)? =
        { LevelSelectContent(viewModel) }


    companion object {
        private const val ARG_PREMIUM_INDEX = "premium_index"
    }

}