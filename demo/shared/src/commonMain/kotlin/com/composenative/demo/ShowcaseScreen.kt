package com.composenative.demo

import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

enum class ShowcaseTab {
    Navigation,
    Counter,
    Form,
    Feed,
    Profile,
    Components,
    Settings
}

/**
 * Showcase Master Screen that hosts and toggles between all demo screens.
 */
class ShowcaseScreen : CNScreen() {
    var selectedTab by mutableStateOf(ShowcaseTab.Navigation)

    private val navigationDemoScreen = NavigationDemoScreen()
    private val counterScreen = CounterScreen()
    private val formScreen = FormInputsScreen()
    private val feedScreen = FeedScreen()
    private val profileScreen = ProfileScreen()
    private val componentsScreen = ComponentsShowcaseScreen()
    private val settingsScreen = SettingsScreen()

    init {
        registerState(counterScreen.run { mutableStateOf(0) }) // Bind nested sub-screens
        navigationDemoScreen.addListener { notifyStateChanged() }
        counterScreen.addListener { notifyStateChanged() }
        formScreen.addListener { notifyStateChanged() }
        feedScreen.addListener { notifyStateChanged() }
        profileScreen.addListener { notifyStateChanged() }
        componentsScreen.addListener { notifyStateChanged() }
        settingsScreen.addListener { notifyStateChanged() }
    }

    override fun build(): CNNode = Column(modifier = Modifier.fillMaxSize()) {
        // Tab Navigation Bar
        add(
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = CNColor.Surface,
                elevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (tab in ShowcaseTab.entries) {
                        val isSelected = selectedTab == tab
                        Button(
                            onClick = { selectedTab = tab },
                            modifier = Modifier
                                .background(
                                    if (isSelected) CNColor.Primary.copyWithAlpha(0.15f) else CNColor.Transparent,
                                    CNShape.RoundedCorner(8.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 5.dp)
                                .haptic(CNHapticType.Light)
                        ) {
                            Text(
                                text = tab.name,
                                color = if (isSelected) CNColor.Primary else CNColor.Gray,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                style = TextStyle.BodySmall
                            )
                        }
                    }
                }
            }
        )

        // Screen Body
        add(
            when (selectedTab) {
                ShowcaseTab.Navigation -> navigationDemoScreen.render()
                ShowcaseTab.Counter -> counterScreen.render()
                ShowcaseTab.Form -> formScreen.render()
                ShowcaseTab.Feed -> feedScreen.render()
                ShowcaseTab.Profile -> profileScreen.render()
                ShowcaseTab.Components -> componentsScreen.render()
                ShowcaseTab.Settings -> settingsScreen.render()
            }
        )
    }
}
