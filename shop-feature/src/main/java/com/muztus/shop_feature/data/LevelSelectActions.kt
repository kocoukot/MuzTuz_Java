package com.muztus.shop_feature.data

import com.muztus.shop_feature.model.ShopItem

interface ShopActions {
    fun itemSelect(selectedItem: ShopItem)

    fun freeCoinsSelect()

    sealed class Base {
        abstract fun handle(action: ShopActions)

        data class OnShopItemSelect(private val selectedItem: ShopItem) : Base() {
            override fun handle(action: ShopActions) = action.itemSelect(selectedItem)
        }

        object OnFreeCoinsSelect : Base() {
            override fun handle(action: ShopActions) = action.freeCoinsSelect()
        }
    }

}
