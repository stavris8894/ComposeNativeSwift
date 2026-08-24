package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Navigation Bar composable matching Material 3 NavigationBar.
 */
fun NavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    items: List<CNNavigationItem>,
    modifier: CNModifier = CNModifier.None,
    containerColor: CNColor = CNColor.Surface,
    contentColor: CNColor = CNColor.Primary
): CNNavigationBarNode = CNNavigationBarNode(
    modifier = modifier,
    items = items,
    selectedIndex = selectedIndex,
    onItemSelected = onItemSelected,
    containerColor = containerColor,
    contentColor = contentColor
)
