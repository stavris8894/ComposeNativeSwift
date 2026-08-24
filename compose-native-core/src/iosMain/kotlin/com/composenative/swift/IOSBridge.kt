package com.composenative.swift

import com.composenative.swift.core.CNNode
import com.composenative.swift.core.CNScreen
import com.composenative.swift.core.CNStateListener

/**
 * iOS Bridge helper for seamless Swift interop.
 */
object ComposeNativeIOS {
    /**
     * Binds a Swift observer to the Kotlin screen and returns a disposable unbind lambda.
     */
    fun observeScreen(screen: CNScreen, onStateChange: () -> Unit): () -> Unit {
        val listener = CNStateListener {
            onStateChange()
        }
        screen.addListener(listener)
        return {
            screen.removeListener(listener)
        }
    }
}
