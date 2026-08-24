package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Top App Bar composable matching Jetpack Compose TopAppBar / CenterAlignedTopAppBar.
 */
fun TopAppBar(
    title: String,
    modifier: CNModifier = CNModifier.None,
    navigationIcon: CNIconButtonNode? = null,
    actions: List<CNNode> = emptyList(),
    backgroundColor: CNColor = CNColor.Surface,
    contentColor: CNColor = CNColor.OnSurface
): CNTopAppBarNode = CNTopAppBarNode(
    modifier = modifier,
    title = title,
    navigationIcon = navigationIcon,
    actions = actions,
    backgroundColor = backgroundColor,
    contentColor = contentColor
)

/**
 * Scaffold layout composable matching Jetpack Compose Scaffold.
 */
fun Scaffold(
    modifier: CNModifier = CNModifier.None,
    topBar: CNTopAppBarNode? = null,
    bottomBar: CNNode? = null,
    floatingActionButton: CNNode? = null,
    content: CNNode
): CNScaffoldNode = CNScaffoldNode(
    modifier = modifier,
    topBar = topBar,
    bottomBar = bottomBar,
    floatingActionButton = floatingActionButton,
    content = content
)

fun Scaffold(
    modifier: CNModifier = CNModifier.None,
    topBar: CNTopAppBarNode? = null,
    bottomBar: CNNode? = null,
    floatingActionButton: CNNode? = null,
    content: () -> CNNode
): CNScaffoldNode = CNScaffoldNode(
    modifier = modifier,
    topBar = topBar,
    bottomBar = bottomBar,
    floatingActionButton = floatingActionButton,
    content = content()
)

