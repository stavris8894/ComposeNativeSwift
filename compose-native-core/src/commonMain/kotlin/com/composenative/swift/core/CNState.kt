package com.composenative.swift.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.reflect.KProperty

/**
 * Observer interface for reacting to state changes in Kotlin from Swift/iOS.
 */
fun interface CNStateListener {
    fun onStateChanged()
}

/**
 * Mutable state container compatible with Jetpack Compose `mutableStateOf`.
 */
class CNMutableState<T>(initialValue: T, private var onValueChanged: (() -> Unit)? = null) {
    var value: T = initialValue
        set(newValue) {
            if (field != newValue) {
                field = newValue
                onValueChanged?.invoke()
            }
        }

    fun bind(listener: () -> Unit) {
        val prev = onValueChanged
        onValueChanged = {
            prev?.invoke()
            listener()
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}

/**
 * Creates a reactive state variable.
 */
fun <T> mutableStateOf(value: T): CNMutableState<T> = CNMutableState(value)

/**
 * Base Screen class for ComposeNative screens.
 * Handles state tracking, lifecycle, and provides high-performance rendering.
 */
abstract class CNScreen {
    val screenScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val listeners = mutableListOf<CNStateListener>()
    private val registeredStates = mutableListOf<CNMutableState<*>>()

    /**
     * Delegate helper that automatically registers a mutableState to trigger screen re-render.
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
     * Builds and returns the root UI node hierarchy.
     */
    abstract fun build(): CNNode

    /**
     * Renders the current screen tree.
     */
    fun render(): CNNode = build()

    /**
     * Registers an observer (e.g. Swift ObservableObject wrapper).
     */
    fun addListener(listener: CNStateListener) {
        listeners.add(listener)
    }

    /**
     * Removes an observer.
     */
    fun removeListener(listener: CNStateListener) {
        listeners.remove(listener)
    }

    /**
     * Triggers re-rendering across all registered native observers.
     */
    fun notifyStateChanged() {
        for (listener in listeners) {
            listener.onStateChanged()
        }
    }

    /**
     * Called when the screen is disposed or removed from the view hierarchy.
     */
    open fun onDispose() {
        listeners.clear()
        screenScope.cancel()
    }
}
