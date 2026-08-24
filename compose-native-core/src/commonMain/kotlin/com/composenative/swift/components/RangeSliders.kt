package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Range Slider matching Jetpack Compose RangeSlider.
 */
fun RangeSlider(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: CNModifier = CNModifier.None,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    enabled: Boolean = true,
    activeColor: CNColor = CNColor.Primary
): CNRangeSliderNode = CNRangeSliderNode(
    modifier = modifier,
    startValue = value.start,
    endValue = value.endInclusive,
    onValuesChange = { start, end -> onValueChange(start..end) },
    valueRange = valueRange,
    steps = steps,
    enabled = enabled,
    activeColor = activeColor
)
