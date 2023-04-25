package com.muztus.core_mvi

import android.content.Intent
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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface BaseFragment<VM : BaseViewModel> {
    val viewModel: VM
    var screenContent: (@Composable ((VM) -> Unit))?

    fun observeData(composeRoute: ((ComposeFragmentRoute) -> Unit)? = null)

    abstract class BaseF<VM : BaseViewModel> : Fragment(), BaseFragment<VM> {

        override fun observeData(composeRoute: ((ComposeFragmentRoute) -> Unit)?) {
            viewModel.observeSteps().onEach { route ->
                when (route) {
                    is ComposeRouteNavigation -> navigateScreen(route)
                    is ComposeRouteOpenWeb -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("https://${route.getWebUrl()}")
                        startActivity(intent)

                    }

                    else -> composeRoute?.invoke(route)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

        override fun onDestroy() {
            super.onDestroy()
            screenContent = null
        }

        private fun navigateScreen(screen: ComposeRouteNavigation) {
            this.findNavController().apply {
                when (screen) {
                    is ComposeRouteNavigation.DeepLinkNavigate -> {
                        if (screen.needPop()) popBackStack()
                        navigate(
                            Uri.parse("${getString(screen.destination())}/${screen.arguments}"),
                            NavOptions.Builder()
                                .setEnterAnim(R.anim.fade_in)
                                .setExitAnim(R.anim.fade_out)
                                .setPopEnterAnim(R.anim.fade_in)
                                .setPopExitAnim(R.anim.fade_out)
                                .build()
                        )
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
                    screenContent?.invoke(viewModel)
                }
            }
        }
    }
}
