package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * SearchBar composable matching Material 3 SearchBar.
 */
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit = {},
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit = {},
    placeholder: String = "Search...",
    modifier: CNModifier = CNModifier.None
): CNSearchBarNode = CNSearchBarNode(
    modifier = modifier,
    query = query,
    onQueryChange = onQueryChange,
    placeholder = placeholder,
    onSearch = onSearch,
    active = active,
    onActiveChange = onActiveChange
)
