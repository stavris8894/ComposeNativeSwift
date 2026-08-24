package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * ListItem composable matching Material 3 ListItem.
 */
fun ListItem(
    headlineContent: () -> CNNode,
    modifier: CNModifier = CNModifier.None,
    supportingContent: (() -> CNNode)? = null,
    leadingContent: (() -> CNNode)? = null,
    trailingContent: (() -> CNNode)? = null,
    overlineContent: (() -> CNNode)? = null,
    onClick: (() -> Unit)? = null
): CNListItemNode = CNListItemNode(
    modifier = modifier,
    headlineContent = headlineContent(),
    supportingContent = supportingContent?.invoke(),
    leadingContent = leadingContent?.invoke(),
    trailingContent = trailingContent?.invoke(),
    overlineContent = overlineContent?.invoke(),
    onClick = onClick
)
