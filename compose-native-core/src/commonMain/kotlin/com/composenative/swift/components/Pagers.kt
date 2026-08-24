package com.composenative.swift.components

import com.composenative.swift.core.*

class CNPagerScope {
    private val pages = mutableListOf<CNNode>()

    fun page(content: () -> CNNode) {
        pages.add(content())
    }

    fun <T> pages(items: List<T>, pageContent: (item: T) -> CNNode) {
        for (item in items) {
            pages.add(pageContent(item))
        }
    }

    fun buildPages(): List<CNNode> = pages.toList()
}

/**
 * Horizontal Pager matching Jetpack Compose Foundation HorizontalPager / SwiftUI Page TabView.
 */
fun HorizontalPager(
    pageCount: Int,
    currentPage: Int = 0,
    onPageChange: (Int) -> Unit = {},
    modifier: CNModifier = CNModifier.None,
    content: CNPagerScope.() -> Unit
): CNHorizontalPagerNode {
    val scope = CNPagerScope()
    scope.content()
    return CNHorizontalPagerNode(
        modifier = modifier,
        pageCount = pageCount,
        currentPage = currentPage,
        onPageChange = onPageChange,
        children = scope.buildPages()
    )
}

/**
 * Vertical Pager matching Jetpack Compose Foundation VerticalPager.
 */
fun VerticalPager(
    pageCount: Int,
    currentPage: Int = 0,
    onPageChange: (Int) -> Unit = {},
    modifier: CNModifier = CNModifier.None,
    content: CNPagerScope.() -> Unit
): CNVerticalPagerNode {
    val scope = CNPagerScope()
    scope.content()
    return CNVerticalPagerNode(
        modifier = modifier,
        pageCount = pageCount,
        currentPage = currentPage,
        onPageChange = onPageChange,
        children = scope.buildPages()
    )
}
