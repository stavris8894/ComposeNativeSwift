package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Accordion / Expandable card composable.
 */
fun Accordion(
    title: String,
    isExpanded: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: CNModifier = CNModifier.None,
    leadingIcon: (() -> CNIconNode)? = null,
    content: () -> CNNode
): CNAccordionNode = CNAccordionNode(
    modifier = modifier,
    title = title,
    isExpanded = isExpanded,
    onToggle = onToggle,
    leadingIcon = leadingIcon?.invoke(),
    content = content()
)

/**
 * Banner composable matching Material 3 Banner.
 */
fun Banner(
    text: String,
    modifier: CNModifier = CNModifier.None,
    icon: (() -> CNIconNode)? = null,
    primaryActionText: String? = null,
    onPrimaryAction: (() -> Unit)? = null,
    secondaryActionText: String? = null,
    onSecondaryAction: (() -> Unit)? = null,
    backgroundColor: CNColor = CNColor.PrimaryContainer
): CNBannerNode = CNBannerNode(
    modifier = modifier,
    text = text,
    icon = icon?.invoke(),
    primaryActionText = primaryActionText,
    onPrimaryAction = onPrimaryAction,
    secondaryActionText = secondaryActionText,
    onSecondaryAction = onSecondaryAction,
    backgroundColor = backgroundColor
)

/**
 * FlowRow layout composable matching Compose Foundation FlowRow.
 */
fun FlowRow(
    modifier: CNModifier = CNModifier.None,
    horizontalArrangement: CNArrangement = CNArrangement.Start,
    verticalArrangement: CNArrangement = CNArrangement.Top,
    maxItemsInEachRow: Int = Int.MAX_VALUE,
    content: CNRowScope.() -> Unit
): CNFlowRowNode {
    val scope = CNRowScope()
    scope.content()
    return CNFlowRowNode(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        maxItemsInEachRow = maxItemsInEachRow,
        children = scope.buildChildren()
    )
}
