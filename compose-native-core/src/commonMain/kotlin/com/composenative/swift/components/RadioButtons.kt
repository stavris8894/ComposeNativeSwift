package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * RadioButton composable matching Jetpack Compose RadioButton.
 */
fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: CNModifier = CNModifier.None,
    label: String? = null,
    enabled: Boolean = true,
    selectedColor: CNColor = CNColor.Primary
): CNRadioButtonNode = CNRadioButtonNode(
    modifier = modifier,
    selected = selected,
    onClick = onClick,
    label = label,
    enabled = enabled,
    selectedColor = selectedColor
)
