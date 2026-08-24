package com.composenative.swift.navigation

import com.composenative.swift.core.CNStateListener

data class CNNavBackStackEntry(
    val route: String,
    val destinationPattern: String,
    val arguments: Map<String, String> = emptyMap(),
    val id: String = "entry-${++entryIdCounter}"
) {
    companion object {
        private var entryIdCounter = 0L
    }
}

data class CNNavOptions(
    val launchSingleTop: Boolean = false,
    val popUpToRoute: String? = null,
    val inclusive: Boolean = false
)

/**
 * Navigation controller managing the active route backstack and transitions.
 */
class CNNavController {
    private val _backStack = mutableListOf<CNNavBackStackEntry>()
    val backStack: List<CNNavBackStackEntry> get() = _backStack.toList()
    val currentEntry: CNNavBackStackEntry? get() = _backStack.lastOrNull()
    val currentRoute: String? get() = currentEntry?.route

    private val listeners = mutableListOf<CNStateListener>()

    fun initialize(startDestination: String, args: Map<String, String> = emptyMap()) {
        if (_backStack.isEmpty()) {
            _backStack.add(
                CNNavBackStackEntry(
                    route = startDestination,
                    destinationPattern = startDestination,
                    arguments = args
                )
            )
            notifyListeners()
        }
    }

    /**
     * Navigates to a specified route with optional arguments and navigation options.
     */
    fun navigate(
        route: String,
        arguments: Map<String, String> = emptyMap(),
        navOptions: CNNavOptions? = null
    ) {
        if (navOptions?.popUpToRoute != null) {
            popUpTo(navOptions.popUpToRoute, navOptions.inclusive)
        }

        if (navOptions?.launchSingleTop == true && currentRoute == route) {
            return
        }

        _backStack.add(
            CNNavBackStackEntry(
                route = route,
                destinationPattern = route.substringBefore("?").substringBefore("/"),
                arguments = arguments
            )
        )
        notifyListeners()
    }

    /**
     * Pops the top destination from the back stack.
     */
    fun popBackStack(): Boolean {
        if (_backStack.size > 1) {
            _backStack.removeAt(_backStack.size - 1)
            notifyListeners()
            return true
        }
        return false
    }

    /**
     * Pops back to a specific route in the back stack.
     */
    fun popUpTo(route: String, inclusive: Boolean = false): Boolean {
        val index = _backStack.indexOfLast { it.route == route || it.destinationPattern == route }
        if (index >= 0) {
            val targetIndex = if (inclusive) index else index + 1
            while (_backStack.size > targetIndex && _backStack.size > 1) {
                _backStack.removeAt(_backStack.size - 1)
            }
            notifyListeners()
            return true
        }
        return false
    }

    fun addListener(listener: CNStateListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: CNStateListener) {
        listeners.remove(listener)
    }

    fun addStateListener(listener: () -> Unit): () -> Unit {
        val stateListener = CNStateListener { listener() }
        listeners.add(stateListener)
        return {
            listeners.remove(stateListener)
        }
    }

    private fun notifyListeners() {
        for (listener in listeners.toList()) {
            listener.onStateChanged()
        }
    }

    fun onDispose() {
        listeners.clear()
    }
}

/**
 * Creates and remembers a CNNavController instance.
 */
fun rememberNavController(): CNNavController = CNNavController()
