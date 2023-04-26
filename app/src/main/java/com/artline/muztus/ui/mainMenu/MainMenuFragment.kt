package com.artline.muztus.ui.mainMenu

import androidx.compose.runtime.Composable
import com.artline.muztus.sounds.GameSoundPlay
import com.artline.muztus.ui.MainActivity
import com.artline.muztus.ui.mainMenu.model.MainMenuRoute
import com.muztus.core.ext.SupportInfoBar
import com.muztus.core_mvi.BaseFragment
import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.UpdateCoins
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainMenuFragment : BaseFragment.BaseF<MainMenuViewModel>(), SupportInfoBar {
    override val viewModel: MainMenuViewModel by viewModel()

    override var screenContent: (@Composable (MainMenuViewModel) -> Unit)? =
        { MainMenuScreenContent(viewModel) }

    override fun observeData(composeRoute: ((ComposeFragmentRoute) -> Unit)?) {
        super.observeData { route ->
            when (route) {
                is MainMenuRoute.PlaySound -> (requireActivity() as GameSoundPlay).playGameSound(
                    route.soundType
                )

                MainMenuRoute.ClearStarts -> (requireActivity() as UpdateCoins).updateCoins()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).infoBarVisibility(true)
    }
}