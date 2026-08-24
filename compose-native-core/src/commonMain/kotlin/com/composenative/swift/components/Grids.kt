package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Scope for LazyVerticalGrid.
 */
class CNGridScope {
    private val items = mutableListOf<CNNode>()

    fun item(content: () -> CNNode) {
        items.add(content())
    }

    fun <T> items(itemsList: List<T>, itemContent: (item: T) -> CNNode) {
        for (item in itemsList) {
            items.add(itemContent(item))
        }
    }

    fun buildChildren(): List<CNNode> = items.toList()
}

/**
 * LazyVerticalGrid composable matching Material 3 / Foundation LazyVerticalGrid.
 */
fun LazyVerticalGrid(
    columns: CNGridCells,
    modifier: CNModifier = CNModifier.None,
    horizontalArrangement: CNArrangement = CNArrangement.spacedBy(8.dp),
    verticalArrangement: CNArrangement = CNArrangement.spacedBy(8.dp),
    contentPadding: CNPadding = CNPadding.Zero,
    content: CNGridScope.() -> Unit
): CNLazyGridNode {
    val scope = CNGridScope()
    scope.content()
    return CNLazyGridNode(
        modifier = modifier,
        columns = columns,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        contentPadding = contentPadding,
        children = scope.buildChildren()
    )
}

object GridCells {
    fun Fixed(count: Int): CNGridCells = CNGridCells.Fixed(count)
    fun Adaptive(minSize: CNDp): CNGridCells = CNGridCells.Adaptive(minSize)
}
