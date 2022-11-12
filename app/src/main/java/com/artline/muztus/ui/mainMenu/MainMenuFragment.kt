package com.artline.muztus.ui.mainMenu

import androidx.compose.runtime.Composable
import com.artline.muztus.ui.MainActivity
import com.muztus.core.ext.SupportInfoBar
import com.muztus.core_mvi.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainMenuFragment : BaseFragment.BaseF<MainMenuViewModel>(), SupportInfoBar {
    override val viewModel: MainMenuViewModel by viewModel()

    override val screenContent: @Composable (MainMenuViewModel) -> Unit =
        { MainMenuScreenContent(viewModel) }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).infoBarVisibility(true)
    }
}