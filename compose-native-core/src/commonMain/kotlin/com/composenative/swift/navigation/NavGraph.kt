package com.composenative.swift.navigation

import com.composenative.swift.core.CNNode

enum class CNNavBarStyle {
    LiquidGlass,
    UltraThin,
    TransparentBlur,
    Solid,
    Hidden
}

data class CNNavDestination(
    val route: String,
    val title: String = "",
    val navBarStyle: CNNavBarStyle = CNNavBarStyle.LiquidGlass,
    val showBackButton: Boolean = true,
    val content: (CNNavBackStackEntry) -> CNNode
)

class CNNavGraphBuilder {
    private val destinations = mutableMapOf<String, CNNavDestination>()

    fun composable(
        route: String,
        title: String = "",
        navBarStyle: CNNavBarStyle = CNNavBarStyle.LiquidGlass,
        showBackButton: Boolean = true,
        content: (CNNavBackStackEntry) -> CNNode
    ) {
        destinations[route] = CNNavDestination(
            route = route,
            title = title,
            navBarStyle = navBarStyle,
            showBackButton = showBackButton,
            content = content
        )
    }

    fun build(): Map<String, CNNavDestination> = destinations.toMap()
}
