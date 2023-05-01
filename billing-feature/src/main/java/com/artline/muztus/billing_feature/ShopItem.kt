package com.artline.muztus.billing_feature

import com.android.billingclient.api.ProductDetails

data class ShopItem(
    val productId: String,
    val description: String,
    val formattedPrice: String,
    val imgRes: Int,
    val prodDetail: ProductDetails
)
