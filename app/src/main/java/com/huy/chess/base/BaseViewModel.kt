package com.huy.chess.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, I, E>(initialState: S) : ViewModel() {

    private val intentChannel: Channel<I> = Channel()

    private var _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private var _event: MutableSharedFlow<E> = MutableSharedFlow()
    val event: SharedFlow<E> = _event.asSharedFlow()

    init {
        observeIntents()
    }

    private fun observeIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                processIntent(it)
            }
        }
    }

    fun sendIntent(intent: I) {
        viewModelScope.launch { intentChannel.send(intent) }
    }

    protected fun sendEvent(event: E) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun updateState(handler: (oldState: S) -> S) {
        _state.value = handler(_state.value)
    }

    abstract fun processIntent(intent: I)
}
