package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * TabRow composable matching Material 3 TabRow.
 */
fun TabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    tabs: List<CNTabItem>,
    modifier: CNModifier = CNModifier.None,
    containerColor: CNColor = CNColor.Surface,
    contentColor: CNColor = CNColor.Primary
): CNTabRowNode = CNTabRowNode(
    modifier = modifier,
    tabs = tabs,
    selectedTabIndex = selectedTabIndex,
    onTabSelected = onTabSelected,
    containerColor = containerColor,
    contentColor = contentColor
)
