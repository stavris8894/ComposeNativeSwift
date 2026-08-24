package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Scope for Column composable.
 */
class CNColumnScope {
    private val children = mutableListOf<CNNode>()

    fun add(node: CNNode) {
        children.add(node)
    }

    operator fun CNNode.unaryPlus() {
        children.add(this)
    }

    fun buildChildren(): List<CNNode> = children.toList()
}

/**
 * Scope for Row composable.
 */
class CNRowScope {
    private val children = mutableListOf<CNNode>()

    fun add(node: CNNode) {
        children.add(node)
    }

    operator fun CNNode.unaryPlus() {
        children.add(this)
    }

    fun buildChildren(): List<CNNode> = children.toList()
}

/**
 * Scope for Box composable.
 */
class CNBoxScope {
    private val children = mutableListOf<CNNode>()

    fun add(node: CNNode) {
        children.add(node)
    }

    operator fun CNNode.unaryPlus() {
        children.add(this)
    }

    fun buildChildren(): List<CNNode> = children.toList()
}

/**
 * Column layout composable matching Jetpack Compose Column.
 */
fun Column(
    modifier: CNModifier = CNModifier.None,
    verticalArrangement: CNArrangement = CNArrangement.Top,
    horizontalAlignment: CNAlignment.Horizontal = CNAlignment.Start,
    content: CNColumnScope.() -> Unit
): CNColumnNode {
    val scope = CNColumnScope()
    scope.content()
    return CNColumnNode(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        children = scope.buildChildren()
    )
}

/**
 * Row layout composable matching Jetpack Compose Row.
 */
fun Row(
    modifier: CNModifier = CNModifier.None,
    horizontalArrangement: CNArrangement = CNArrangement.Start,
    verticalAlignment: CNAlignment.Vertical = CNAlignment.Top,
    content: CNRowScope.() -> Unit
): CNRowNode {
    val scope = CNRowScope()
    scope.content()
    return CNRowNode(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        children = scope.buildChildren()
    )
}

/**
 * Box layout composable matching Jetpack Compose Box.
 */
fun Box(
    modifier: CNModifier = CNModifier.None,
    contentAlignment: CNAlignment = CNAlignment.TopStart,
    content: CNBoxScope.() -> Unit
): CNBoxNode {
    val scope = CNBoxScope()
    scope.content()
    return CNBoxNode(
        modifier = modifier,
        contentAlignment = contentAlignment,
        children = scope.buildChildren()
    )
}

/**
 * Spacer composable matching Jetpack Compose Spacer.
 */
fun Spacer(modifier: CNModifier): CNSpacerNode = CNSpacerNode(modifier = modifier)

/**
 * Divider composable matching Jetpack Compose HorizontalDivider / Divider.
 */
fun Divider(
    modifier: CNModifier = CNModifier.None,
    thickness: CNDp = 1.dp,
    color: CNColor = CNColor.Divider
): CNDividerNode = CNDividerNode(
    modifier = modifier,
    thickness = thickness,
    color = color
)

/**
 * Card container composable matching Jetpack Compose Card.
 */
fun Card(
    modifier: CNModifier = CNModifier.None,
    shape: CNShape = CNShape.RoundedCorner(12.dp),
    elevation: CNDp = 2.dp,
    border: CNBorder? = null,
    backgroundColor: CNColor = CNColor.Surface,
    content: () -> CNNode
): CNCardNode = CNCardNode(
    modifier = modifier,
    shape = shape,
    elevation = elevation,
    border = border,
    backgroundColor = backgroundColor,
    content = content()
)

/**
 * Surface container composable matching Jetpack Compose Surface.
 */
fun Surface(
    modifier: CNModifier = CNModifier.None,
    shape: CNShape = CNShape.Rectangle,
    color: CNColor = CNColor.Surface,
    contentColor: CNColor = CNColor.OnSurface,
    border: CNBorder? = null,
    elevation: CNDp = 0.dp,
    content: () -> CNNode
): CNSurfaceNode = CNSurfaceNode(
    modifier = modifier,
    shape = shape,
    color = color,
    contentColor = contentColor,
    border = border,
    elevation = elevation,
    content = content()
)
