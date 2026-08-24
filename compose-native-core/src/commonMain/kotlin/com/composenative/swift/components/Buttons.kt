package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Standard filled Button matching Jetpack Compose Button.
 */
fun Button(
    onClick: () -> Unit,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true,
    shape: CNShape = CNShape.RoundedCorner(8.dp),
    content: () -> CNNode
): CNButtonNode = CNButtonNode(
    modifier = modifier,
    onClick = onClick,
    style = CNButtonStyle.Filled,
    enabled = enabled,
    content = content()
)

/**
 * Outlined Button matching Jetpack Compose OutlinedButton.
 */
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true,
    shape: CNShape = CNShape.RoundedCorner(8.dp),
    border: CNBorder = CNBorder(1.dp, CNColor.Primary),
    content: () -> CNNode
): CNButtonNode = CNButtonNode(
    modifier = modifier.border(border),
    onClick = onClick,
    style = CNButtonStyle.Outlined,
    enabled = enabled,
    content = content()
)

/**
 * Text Button matching Jetpack Compose TextButton.
 */
fun TextButton(
    onClick: () -> Unit,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true,
    content: () -> CNNode
): CNButtonNode = CNButtonNode(
    modifier = modifier,
    onClick = onClick,
    style = CNButtonStyle.Text,
    enabled = enabled,
    content = content()
)

/**
 * Icon Button matching Jetpack Compose IconButton.
 */
fun IconButton(
    onClick: () -> Unit,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true,
    icon: () -> CNIconNode
): CNIconButtonNode = CNIconButtonNode(
    modifier = modifier,
    onClick = onClick,
    enabled = enabled,
    icon = icon()
)

/**
 * Floating Action Button matching Jetpack Compose FloatingActionButton.
 */
fun FloatingActionButton(
    onClick: () -> Unit,
    modifier: CNModifier = CNModifier.None,
    shape: CNShape = CNShape.RoundedCorner(16.dp),
    containerColor: CNColor = CNColor.Primary,
    contentColor: CNColor = CNColor.OnPrimary,
    elevation: CNDp = 6.dp,
    content: () -> CNNode
): CNButtonNode = CNButtonNode(
    modifier = modifier
        .size(56.dp)
        .background(containerColor, shape)
        .shadow(elevation, shape),
    onClick = onClick,
    style = CNButtonStyle.Filled,
    enabled = true,
    content = content()
)
