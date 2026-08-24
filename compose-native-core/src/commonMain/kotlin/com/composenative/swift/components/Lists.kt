package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Scope for LazyColumn and LazyRow composables.
 */
class CNLazyListScope {
    private val items = mutableListOf<CNNode>()

    fun item(content: () -> CNNode) {
        items.add(content())
    }

    fun <T> items(itemsList: List<T>, itemContent: (item: T) -> CNNode) {
        for (item in itemsList) {
            items.add(itemContent(item))
        }
    }

    fun <T> itemsIndexed(itemsList: List<T>, itemContent: (index: Int, item: T) -> CNNode) {
        itemsList.forEachIndexed { index, item ->
            items.add(itemContent(index, item))
        }
    }

    fun buildChildren(): List<CNNode> = items.toList()
}

/**
 * LazyColumn composable matching Jetpack Compose LazyColumn.
 */
fun LazyColumn(
    modifier: CNModifier = CNModifier.None,
    verticalArrangement: CNArrangement = CNArrangement.Top,
    contentPadding: CNPadding = CNPadding.Zero,
    content: CNLazyListScope.() -> Unit
): CNLazyColumnNode {
    val scope = CNLazyListScope()
    scope.content()
    return CNLazyColumnNode(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        contentPadding = contentPadding,
        children = scope.buildChildren()
    )
}

/**
 * LazyRow composable matching Jetpack Compose LazyRow.
 */
fun LazyRow(
    modifier: CNModifier = CNModifier.None,
    horizontalArrangement: CNArrangement = CNArrangement.Start,
    contentPadding: CNPadding = CNPadding.Zero,
    content: CNLazyListScope.() -> Unit
): CNLazyRowNode {
    val scope = CNLazyListScope()
    scope.content()
    return CNLazyRowNode(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        contentPadding = contentPadding,
        children = scope.buildChildren()
    )
}
