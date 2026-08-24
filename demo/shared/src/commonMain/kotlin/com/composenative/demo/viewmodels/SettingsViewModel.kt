package com.composenative.demo.viewmodels

import com.composenative.swift.core.CNViewModel

class SettingsViewModel : CNViewModel() {
    var darkModeEnabled by mutableStateOf(false)
    var notificationsEnabled by mutableStateOf(true)
    var biometricAuthEnabled by mutableStateOf(true)
    var volumeLevel by mutableStateOf(0.75f)

    fun toggleDarkMode(enabled: Boolean) {
        darkModeEnabled = enabled
    }

    fun toggleNotifications(enabled: Boolean) {
        notificationsEnabled = enabled
    }

    fun toggleBiometrics(enabled: Boolean) {
        biometricAuthEnabled = enabled
    }

    fun updateVolume(level: Float) {
        volumeLevel = level.coerceIn(0f, 1f)
    }
}
