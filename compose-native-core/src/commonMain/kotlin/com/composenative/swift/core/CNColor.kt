package com.composenative.swift.core

import kotlin.math.roundToInt

/**
 * Cross-platform Color representation for ComposeNative.
 * Seamlessly maps to SwiftUI.Color on iOS and androidx.compose.ui.graphics.Color on Android.
 */
data class CNColor(
    val red: Int,
    val green: Int,
    val blue: Int,
    val alpha: Float = 1.0f,
    val name: String? = null
) {
    init {
        require(red in 0..255) { "Red must be between 0 and 255" }
        require(green in 0..255) { "Green must be between 0 and 255" }
        require(blue in 0..255) { "Blue must be between 0 and 255" }
        require(alpha in 0f..1f) { "Alpha must be between 0.0 and 1.0" }
    }

    constructor(hex: String, name: String? = null) : this(
        red = parseHexComponent(hex, 0),
        green = parseHexComponent(hex, 1),
        blue = parseHexComponent(hex, 2),
        alpha = parseHexAlpha(hex),
        name = name
    )

    fun toHex(): String {
        val a = (alpha * 255).roundToInt().toString(16).padStart(2, '0')
        val r = red.toString(16).padStart(2, '0')
        val g = green.toString(16).padStart(2, '0')
        val b = blue.toString(16).padStart(2, '0')
        return "#$a$r$g$b".uppercase()
    }

    fun copyWithAlpha(newAlpha: Float): CNColor = copy(alpha = newAlpha.coerceIn(0f, 1f))

    companion object {
        private fun parseHexComponent(hex: String, index: Int): Int {
            val cleanHex = hex.removePrefix("#")
            return when (cleanHex.length) {
                6 -> cleanHex.substring(index * 2, index * 2 + 2).toInt(16)
                8 -> cleanHex.substring((index + 1) * 2, (index + 1) * 2 + 2).toInt(16)
                3 -> cleanHex.substring(index, index + 1).repeat(2).toInt(16)
                else -> 0
            }
        }

        private fun parseHexAlpha(hex: String): Float {
            val cleanHex = hex.removePrefix("#")
            return if (cleanHex.length == 8) {
                cleanHex.substring(0, 2).toInt(16) / 255f
            } else {
                1.0f
            }
        }

        // Standard Theme Colors
        val Primary = CNColor(0x00, 0x7A, 0xFF, name = "primary") // System Blue
        val OnPrimary = CNColor(0xFF, 0xFF, 0xFF, name = "onPrimary")
        val PrimaryContainer = CNColor(0xE3, 0xF2, 0xFD, name = "primaryContainer")
        val Secondary = CNColor(0x58, 0x56, 0xD6, name = "secondary") // System Purple
        val OnSecondary = CNColor(0xFF, 0xFF, 0xFF, name = "onSecondary")
        val Accent = CNColor(0xFF, 0x95, 0x00, name = "accent") // System Orange
        
        val Background = CNColor(0xF2, 0xF2, 0xF7, name = "background") // System Grouped Background
        val OnBackground = CNColor(0x1C, 0x1C, 0x1E, name = "onBackground")
        val Surface = CNColor(0xFF, 0xFF, 0xFF, name = "surface")
        val OnSurface = CNColor(0x1C, 0x1C, 0x1E, name = "onSurface")
        val SurfaceVariant = CNColor(0xE5, 0xE5, 0xEA, name = "surfaceVariant")
        val OnSurfaceVariant = CNColor(0x8E, 0x8E, 0x93, name = "onSurfaceVariant")
        
        val Error = CNColor(0xFF, 0x3B, 0x30, name = "error") // System Red
        val OnError = CNColor(0xFF, 0xFF, 0xFF, name = "onError")
        val Success = CNColor(0x34, 0xC7, 0x59, name = "success") // System Green
        val Warning = CNColor(0xFF, 0xCC, 0x00, name = "warning") // System Yellow
        val Info = CNColor(0x32, 0xAD, 0xE6, name = "info") // System Teal
        
        val White = CNColor(0xFF, 0xFF, 0xFF, name = "white")
        val Black = CNColor(0x00, 0x00, 0x00, name = "black")
        val Transparent = CNColor(0, 0, 0, 0f, name = "transparent")
        val Current = CNColor(0, 0, 0, 1f, name = "current")
        val Gray = CNColor(0x8E, 0x8E, 0x93, name = "gray")
        val LightGray = CNColor(0xD1, 0xD1, 0xD6, name = "lightGray")
        val DarkGray = CNColor(0x3A, 0x3A, 0x3C, name = "darkGray")
        val Divider = CNColor(0xC6, 0xC6, 0xC8, 0.6f, name = "divider")
    }
}

typealias Color = CNColor

