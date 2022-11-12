package com.muztus.core_mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface BaseViewModel : RouteCommunication {

    abstract class Base<State, Action>(
        private val mRoute: Channel<ComposeFragmentRoute> = Channel(),
        private val mState: MutableStateFlow<State>,
    ) : ViewModel(), BaseViewModel, StateCommunication<State> {

        override val state = mState.asStateFlow()
        override fun observeSteps(): Flow<ComposeFragmentRoute> = mRoute.receiveAsFlow()

        override fun sendRoute(event: ComposeFragmentRoute) {
            viewModelScope.launch { mRoute.send(event) }
        }

        override fun updateInfo(info: State.() -> State) {
            viewModelScope.launch {
                mState.update { info.invoke(it) }
            }
        }

        abstract fun setInputActions(action: Action)

        protected open fun clearErrorText() {}

        protected fun getState() = mState.value

        protected fun <Response> networkRequest(
            action: suspend () -> Response,
            onSuccess: (Response) -> Unit,
            onFailure: (Throwable) -> Unit,
            onComplete: (() -> Unit)? = null
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching { action.invoke() }
                    .onSuccess {
                        withContext(Dispatchers.Main) {
                            onSuccess.invoke(it)
                        }
                    }
                    .onFailure { error ->
                        onFailure.invoke(error)
                    }.also { onComplete?.invoke() }
            }
        }
    }

}

interface RouteCommunication {
    fun observeSteps(): Flow<ComposeFragmentRoute>

    fun sendRoute(event: ComposeFragmentRoute)
}