package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Segmented Button Row matching Material 3 SingleChoiceSegmentedButtonRow.
 */
fun SegmentedButtonRow(
    selectedIndex: Int,
    onSelectIndex: (Int) -> Unit,
    items: List<CNSegmentItem>,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true
): CNSegmentedButtonNode = CNSegmentedButtonNode(
    modifier = modifier,
    items = items,
    selectedIndex = selectedIndex,
    onSelectIndex = onSelectIndex,
    enabled = enabled
)
