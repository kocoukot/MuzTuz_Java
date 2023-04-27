package com.muztus.shop_feature.data

import com.muztus.shop_feature.model.ShopItem

data class ShopState(
    val error: String = "",
    val isLoading: Boolean = false,
    val productList: List<ShopItem> = emptyList()
)