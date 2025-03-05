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

sealed class NoEffect

abstract class BaseViewModel<S, A, E>(initialState: S) : ViewModel() {

    private val actionChannel: Channel<A> = Channel()

    private var _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private var _event: MutableSharedFlow<E> = MutableSharedFlow()
    val event: SharedFlow<E> = _event.asSharedFlow()

    init {
        observeAction()
    }

    private fun observeAction() {
        viewModelScope.launch {
            actionChannel.consumeAsFlow().collect {
                processAction(it)
            }
        }
    }

    fun sendAction(action: A) {
        viewModelScope.launch { actionChannel.send(action) }
    }

    protected fun sendEffect(effect : E) {
        viewModelScope.launch { _event.emit(effect) }
    }

    protected fun updateState(handler: (oldState: S) -> S) {
        _state.value = handler(_state.value)
    }

    abstract fun processAction(action: A)
}
