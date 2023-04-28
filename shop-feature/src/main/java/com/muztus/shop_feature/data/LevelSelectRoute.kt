package com.muztus.shop_feature.data

import com.artline.muztus.sounds.GameSound
import com.muztus.core_mvi.ComposeFragmentRoute
import com.muztus.shop_feature.model.ShopItem

sealed class ShopRoute : ComposeFragmentRoute {
    object UpdateCoins : ShopRoute()

    data class PlaySound(val soundType: GameSound) : ShopRoute()
    data class ShopItemSelect(val selectedItem: ShopItem) : ShopRoute()
    object ShowAd : ShopRoute()
}