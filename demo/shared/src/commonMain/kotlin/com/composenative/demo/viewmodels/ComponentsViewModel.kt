package com.composenative.demo.viewmodels

import com.composenative.swift.core.CNViewModel

class ComponentsViewModel : CNViewModel() {
    var currentPage by mutableStateOf(0)
    var showSnackbar by mutableStateOf(false)
    var snackbarMessage by mutableStateOf("")
    var selectedSegment by mutableStateOf(0)
    var selectedChipIndex by mutableStateOf(0)
    var switchState by mutableStateOf(true)
    var sliderVal by mutableStateOf(65f)
    var rangeMin by mutableStateOf(0.2f)
    var rangeMax by mutableStateOf(0.8f)

    val pagerItems = listOf(
        Triple("Zero Overhead", "Translates Compose Virtual Trees directly to genuine SwiftUI native views.", "bolt.fill"),
        Triple("Native 120 FPS", "Full Apple ProMotion & CoreAnimation rendering without canvas layers.", "speedometer"),
        Triple("Material 3 & Dark Mode", "Automatic dynamic color resolution matching iOS system color schemes.", "moon.stars.fill")
    )

    fun triggerFeedback(message: String) {
        snackbarMessage = message
        showSnackbar = true
    }

    fun dismissFeedback() {
        showSnackbar = false
    }
}
