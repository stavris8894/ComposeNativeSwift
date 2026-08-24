package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Filter Chip matching Jetpack Compose FilterChip.
 */
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: () -> CNNode,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true,
    leadingIcon: (() -> CNIconNode)? = null,
    trailingIcon: (() -> CNIconNode)? = null
): CNChipNode {
    val content = label()
    val text = if (content is CNTextNode) content.text else ""
    return CNChipNode(
        modifier = modifier,
        text = text,
        selected = selected,
        onClick = onClick,
        leadingIcon = leadingIcon?.invoke(),
        trailingIcon = trailingIcon?.invoke(),
        chipType = CNChipType.Filter,
        enabled = enabled
    )
}

/**
 * Assist Chip matching Jetpack Compose AssistChip.
 */
fun AssistChip(
    onClick: () -> Unit,
    label: () -> CNNode,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true,
    leadingIcon: (() -> CNIconNode)? = null,
    trailingIcon: (() -> CNIconNode)? = null
): CNChipNode {
    val content = label()
    val text = if (content is CNTextNode) content.text else ""
    return CNChipNode(
        modifier = modifier,
        text = text,
        selected = false,
        onClick = onClick,
        leadingIcon = leadingIcon?.invoke(),
        trailingIcon = trailingIcon?.invoke(),
        chipType = CNChipType.Assist,
        enabled = enabled
    )
}

/**
 * Suggestion Chip matching Jetpack Compose SuggestionChip.
 */
fun SuggestionChip(
    onClick: () -> Unit,
    label: () -> CNNode,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true,
    leadingIcon: (() -> CNIconNode)? = null
): CNChipNode {
    val content = label()
    val text = if (content is CNTextNode) content.text else ""
    return CNChipNode(
        modifier = modifier,
        text = text,
        selected = false,
        onClick = onClick,
        leadingIcon = leadingIcon?.invoke(),
        chipType = CNChipType.Suggestion,
        enabled = enabled
    )
}

/**
 * Input Chip matching Jetpack Compose InputChip.
 */
fun InputChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: () -> CNNode,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true,
    leadingIcon: (() -> CNIconNode)? = null,
    trailingIcon: (() -> CNIconNode)? = null
): CNChipNode {
    val content = label()
    val text = if (content is CNTextNode) content.text else ""
    return CNChipNode(
        modifier = modifier,
        text = text,
        selected = selected,
        onClick = onClick,
        leadingIcon = leadingIcon?.invoke(),
        trailingIcon = trailingIcon?.invoke(),
        chipType = CNChipType.Input,
        enabled = enabled
    )
}
