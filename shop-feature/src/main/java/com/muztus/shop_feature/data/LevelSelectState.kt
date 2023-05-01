package com.muztus.shop_feature.data

import com.artline.muztus.billing_feature.ShopItem

data class ShopState(
    val error: String = "",
    val isLoading: Boolean = false,
    val productList: List<ShopItem> = emptyList()
)