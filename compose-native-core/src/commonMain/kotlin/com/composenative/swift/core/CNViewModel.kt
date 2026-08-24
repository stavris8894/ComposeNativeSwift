package com.composenative.swift.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Base ViewModel class for ComposeNative Kotlin Multiplatform applications.
 * Encapsulates state, business logic, and coroutine execution in common code.
 */
abstract class CNViewModel {
    val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val listeners = mutableListOf<CNStateListener>()
    private val registeredStates = mutableListOf<CNMutableState<*>>()

    /**
     * Creates and registers a reactive mutable state property on this ViewModel.
     * Whenever this state changes, any observing screen will automatically re-render.
     */
    protected fun <T> mutableStateOf(initialValue: T): CNMutableState<T> {
        val state = CNMutableState(initialValue)
        registerState(state)
        return state
    }

    fun registerState(state: CNMutableState<*>) {
        registeredStates.add(state)
        state.bind {
            notifyStateChanged()
        }
    }

    /**
     * Subscribes a listener to state changes.
     */
    fun addListener(listener: CNStateListener) {
        listeners.add(listener)
    }

    /**
     * Unsubscribes a listener.
     */
    fun removeListener(listener: CNStateListener) {
        listeners.remove(listener)
    }

    /**
     * Helper for functional state listeners.
     */
    fun addStateListener(listener: () -> Unit): () -> Unit {
        val stateListener = CNStateListener { listener() }
        listeners.add(stateListener)
        return {
            listeners.remove(stateListener)
        }
    }

    /**
     * Notifies all observers that state has mutated.
     */
    fun notifyStateChanged() {
        for (listener in listeners.toList()) {
            listener.onStateChanged()
        }
    }

    /**
     * Cleans up coroutines and observers when this ViewModel is discarded.
     */
    open fun onCleared() {
        listeners.clear()
        registeredStates.clear()
        viewModelScope.cancel()
    }
}

/**
 * Convenience base screen that binds to a CNViewModel in common Kotlin code.
 */
abstract class CNScreenWithViewModel<VM : CNViewModel>(val viewModel: VM) : CNScreen() {
    init {
        viewModel.addListener { notifyStateChanged() }
    }

    override fun onDispose() {
        super.onDispose()
        viewModel.onCleared()
    }
}
