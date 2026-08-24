package com.composenative.demo.viewmodels

import com.composenative.demo.ShowcaseTab
import com.composenative.swift.core.CNViewModel

class ShowcaseViewModel : CNViewModel() {
    var selectedTab by mutableStateOf(ShowcaseTab.Navigation)
    var isDarkTheme by mutableStateOf(false)

    val navigationViewModel = NavigationViewModel()
    val counterViewModel = CounterViewModel()
    val formViewModel = FormViewModel()
    val feedViewModel = FeedViewModel()
    val profileViewModel = ProfileViewModel()
    val componentsViewModel = ComponentsViewModel()
    val settingsViewModel = SettingsViewModel()

    init {
        navigationViewModel.addListener { notifyStateChanged() }
        counterViewModel.addListener { notifyStateChanged() }
        formViewModel.addListener { notifyStateChanged() }
        feedViewModel.addListener { notifyStateChanged() }
        profileViewModel.addListener { notifyStateChanged() }
        componentsViewModel.addListener { notifyStateChanged() }
        settingsViewModel.addListener {
            if (isDarkTheme != settingsViewModel.darkModeEnabled) {
                isDarkTheme = settingsViewModel.darkModeEnabled
            }
            notifyStateChanged()
        }
    }

    fun selectTab(tab: ShowcaseTab) {
        selectedTab = tab
    }

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
        settingsViewModel.darkModeEnabled = isDarkTheme
    }

    override fun onCleared() {
        super.onCleared()
        navigationViewModel.onCleared()
        counterViewModel.onCleared()
        formViewModel.onCleared()
        feedViewModel.onCleared()
        profileViewModel.onCleared()
        componentsViewModel.onCleared()
        settingsViewModel.onCleared()
    }
}
