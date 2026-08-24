package com.composenative.demo

import com.composenative.demo.viewmodels.ShowcaseViewModel
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
 * Showcase Master Screen hosting all demo features.
 * All state and business logic reside in ShowcaseViewModel in Common Kotlin!
 */
class ShowcaseScreen(
    viewModel: ShowcaseViewModel
) : CNScreenWithViewModel<ShowcaseViewModel>(viewModel) {
    constructor() : this(ShowcaseViewModel())

    private val navigationScreen = NavigationDemoScreen(viewModel.navigationViewModel)
    private val counterScreen = CounterScreen(viewModel.counterViewModel)
    private val formScreen = FormInputsScreen(viewModel.formViewModel)
    private val feedScreen = FeedScreen(viewModel.feedViewModel)
    private val profileScreen = ProfileScreen(viewModel.profileViewModel)
    private val componentsScreen = ComponentsShowcaseScreen(viewModel.componentsViewModel)
    private val settingsScreen = SettingsScreen(viewModel.settingsViewModel)

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
                        val isSelected = viewModel.selectedTab == tab
                        Button(
                            onClick = { viewModel.selectTab(tab) },
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

        // Screen Body powered by ViewModel
        add(
            when (viewModel.selectedTab) {
                ShowcaseTab.Navigation -> navigationScreen.render()
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
