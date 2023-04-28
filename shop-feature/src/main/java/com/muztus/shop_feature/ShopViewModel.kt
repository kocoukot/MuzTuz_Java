package com.muztus.shop_feature

import com.muztus.core_mvi.BaseViewModel
import com.muztus.domain_layer.usecase.SetCoinsAmountUseCase
import com.muztus.shop_feature.data.ShopActions
import com.muztus.shop_feature.data.ShopRoute
import com.muztus.shop_feature.data.ShopState
import com.muztus.shop_feature.model.ShopItem
import kotlinx.coroutines.flow.MutableStateFlow

class ShopViewModel(
    private val setCoinsAmountUseCase: SetCoinsAmountUseCase,
) : BaseViewModel.Base<ShopState, ShopActions.Base>(
    mState = MutableStateFlow(ShopState())
), ShopActions {

    override fun setInputActions(action: ShopActions.Base) {
        action.handle(this)
    }

    override fun itemSelect(selectedItem: ShopItem) {
        sendRoute(ShopRoute.ShopItemSelect(selectedItem))
    }

    override fun freeCoinsSelect() {
        sendRoute(ShopRoute.ShowAd)
    }

    fun onProductListGot(list: List<ShopItem>) {
        updateInfo {
            copy(productList = list)
        }
    }

    fun onCoinsBought(bought: Int) {
        setCoinsAmountUseCase.invoke(bought)
    }
}
