package com.muztus.domain_layer.model

import androidx.annotation.DrawableRes
import com.muztus.domain_layer.R

enum class SocialMediaTypes(@DrawableRes val image: Int, val webLink: String) {
    VK(R.drawable.img_vkontakte, "vk.com"),
    TELEGRAM(R.drawable.img_telegram, "t.me\\kocoukot"),
//    INSTAGRAM(R.drawable.vk),
}