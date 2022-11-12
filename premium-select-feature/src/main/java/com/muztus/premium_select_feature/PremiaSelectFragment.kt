package com.muztus.premium_select_feature

import androidx.compose.runtime.Composable
import com.muztus.core.ext.SupportInfoBar
import com.muztus.core_mvi.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PremiaSelectFragment : BaseFragment.BaseF<PremiaSelectViewModel>(), SupportInfoBar {
    override val viewModel: PremiaSelectViewModel by viewModel()

    override val screenContent: @Composable (PremiaSelectViewModel) -> Unit =
        { PremiaSelectContent(viewModel) }
}