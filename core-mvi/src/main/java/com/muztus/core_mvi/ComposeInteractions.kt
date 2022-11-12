package com.muztus.core_mvi

import android.os.Bundle
import kotlinx.coroutines.flow.StateFlow

interface ComposeFragmentRoute

interface ComposeRouteNavigation {
    fun needPop(): Boolean = false
    fun destination(): Int = 0

    interface DeepLinkNavigate : ComposeRouteNavigation {
        val arguments: String
            get() = ""
    }

    interface GraphNavigate : ComposeRouteNavigation {
        val bundle: Bundle?
            get() = null
    }

    interface PopNavigation : ComposeRouteNavigation
    interface NavigateToStart : ComposeRouteNavigation
    interface ComposeRouteFinishApp : ComposeRouteNavigation
}


interface ComposeRouteCallNumber {
    fun callPhoneNumber(): String
}

interface ComposeRouteOpenWeb {
    fun getWebUrl(): String
}

interface StateCommunication<T> : DataUpdate<T> {
    val state: StateFlow<T>
}

interface DataUpdate<T> {
    fun updateInfo(info: T.() -> T)
}


