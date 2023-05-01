package com.muztus.shop_feature

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.artline.muztus.billing_feature.BillingClientService
import com.artline.muztus.sounds.GameSound
import com.artline.muztus.sounds.GameSoundPlay
import com.muztus.core.ext.SupportInfoBar
import com.muztus.core_mvi.AdsActivity
import com.muztus.core_mvi.BaseFragment
import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.core_mvi.UpdateCoins
import com.muztus.shop_feature.data.ShopRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopFragment : BaseFragment.BaseF<ShopViewModel>(), SupportInfoBar {
    override val viewModel: ShopViewModel by viewModel()

    private val billingClient by lazy {
        BillingClientService(
            onProductsReceived = { mappedList ->
                viewModel.onProductListGot(mappedList)
            },
            onProductSuccess = this::onCoinsGot,
        ).apply {
            clientInit(requireContext())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        billingClient.billStartConnection()
    }


    override var screenContent: (@Composable (ShopViewModel) -> Unit)? =
        { ShopScreenContent(viewModel) }


    override fun observeData(composeRoute: ((ComposeFragmentRoute) -> Unit)?) {
        super.observeData { route ->
            when (route) {
                is ShopRoute.ShopItemSelect -> {
                    billingClient.launchBillFlow(route.selectedItem.prodDetail, requireActivity())
                }

                ShopRoute.ShowAd -> (requireActivity() as AdsActivity).showAd()
            }
        }
    }

    private fun onCoinsGot(amount: Int) {
        viewModel.onCoinsBought(amount)
        lifecycleScope.launch {
            delay(100)
            (requireActivity() as GameSoundPlay).playGameSound(GameSound.SoundGotCoins)
            (requireActivity() as UpdateCoins).updateCoins()
        }
    }
}






