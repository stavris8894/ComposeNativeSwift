package com.composenative.swift.components

import com.composenative.swift.core.*

class CNMenuScope {
    private val items = mutableListOf<CNMenuItem>()

    fun item(
        title: String,
        icon: String? = null,
        isDestructive: Boolean = false,
        enabled: Boolean = true,
        onClick: () -> Unit
    ) {
        items.add(
            CNMenuItem(
                id = generateNodeId("menu_item"),
                title = title,
                icon = icon,
                isDestructive = isDestructive,
                enabled = enabled,
                onClick = onClick
            )
        )
    }

    fun buildItems(): List<CNMenuItem> = items.toList()
}

/**
 * Dropdown Menu composable matching Material 3 DropdownMenu / SwiftUI.Menu.
 */
fun DropdownMenu(
    title: String = "Menu",
    modifier: CNModifier = CNModifier.None,
    triggerContent: (() -> CNNode)? = null,
    content: CNMenuScope.() -> Unit
): CNMenuNode {
    val scope = CNMenuScope()
    scope.content()
    return CNMenuNode(
        modifier = modifier,
        title = title,
        items = scope.buildItems(),
        triggerContent = triggerContent?.invoke()
    )
}
