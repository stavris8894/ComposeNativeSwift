package com.composenative.swift.navigation

import com.composenative.swift.core.*

/**
 * NavHost composable matching Jetpack Compose Navigation NavHost.
 * Connects directly to SwiftUI NavigationStack on iOS.
 */
fun NavHost(
    navController: CNNavController,
    startDestination: String,
    modifier: CNModifier = CNModifier.None,
    builder: CNNavGraphBuilder.() -> Unit
): CNNode {
    val graphBuilder = CNNavGraphBuilder()
    graphBuilder.builder()
    val destinations = graphBuilder.build()

    navController.initialize(startDestination)

    val currentEntry = navController.currentEntry ?: CNNavBackStackEntry(
        route = startDestination,
        destinationPattern = startDestination
    )

    // Lookup matching destination
    val destination = destinations[currentEntry.route]
        ?: destinations[currentEntry.destinationPattern]
        ?: destinations[startDestination]
        ?: destinations.values.firstOrNull()

    val renderedContent = destination?.content?.invoke(currentEntry) ?: CNEmptyNode()

    return CNNavHostNode(
        modifier = modifier,
        activeRoute = currentEntry.route,
        backStackCount = navController.backStack.size,
        currentTitle = destination?.title ?: "",
        navBarStyle = destination?.navBarStyle?.name ?: CNNavBarStyle.LiquidGlass.name,
        showBackButton = destination?.showBackButton == true && navController.backStack.size > 1,
        onPopBack = { navController.popBackStack() },
        content = renderedContent
    )
}
