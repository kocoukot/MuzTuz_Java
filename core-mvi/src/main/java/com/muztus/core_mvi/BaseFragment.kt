package com.muztus.core_mvi

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface BaseFragment<VM : BaseViewModel> {
    val viewModel: VM
    val screenContent: @Composable ((VM) -> Unit)

    fun observeData(composeRoute: ((ComposeFragmentRoute) -> Unit)? = null)

    abstract class BaseF<VM : BaseViewModel> : Fragment(), BaseFragment<VM> {

        override fun observeData(composeRoute: ((ComposeFragmentRoute) -> Unit)?) {
            viewModel.observeSteps().onEach { route ->
                when (route) {
                    is ComposeRouteNavigation -> navigateScreen(route)
                    else -> composeRoute?.invoke(route)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

        private fun navigateScreen(screen: ComposeRouteNavigation) {
            this.findNavController().apply {
                when (screen) {
                    is ComposeRouteNavigation.DeepLinkNavigate -> {
                        if (screen.needPop()) popBackStack()
                        navigate(Uri.parse("${getString(screen.destination())}/${screen.arguments}"))
                    }
                    is ComposeRouteNavigation.GraphNavigate -> {
                        if (screen.needPop()) popBackStack()
                        navigate(screen.destination(), screen.bundle)
                    }
                    is ComposeRouteNavigation.ComposeRouteFinishApp -> requireActivity().finish()
                    is ComposeRouteNavigation.PopNavigation -> popBackStack()
                }
            }
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            observeData()
            return ComposeView(requireContext()).apply {
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
                )
                setContent {
                    screenContent.invoke(viewModel)
                }
            }
        }
    }
}
