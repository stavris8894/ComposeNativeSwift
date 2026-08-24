package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Stepper composable matching iOS SwiftUI Stepper / Material Numeric Stepper.
 */
fun Stepper(
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: CNModifier = CNModifier.None,
    range: ClosedFloatingPointRange<Double> = 0.0..100.0,
    step: Double = 1.0,
    label: String = "",
    enabled: Boolean = true
): CNStepperNode = CNStepperNode(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    range = range,
    step = step,
    label = label,
    enabled = enabled
)

/**
 * Star / Rating Bar composable.
 */
fun RatingBar(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: CNModifier = CNModifier.None,
    maxRating: Int = 5,
    enabled: Boolean = true,
    activeColor: CNColor = CNColor.Accent
): CNRatingBarNode = CNRatingBarNode(
    modifier = modifier,
    rating = rating,
    maxRating = maxRating,
    onRatingChange = onRatingChange,
    enabled = enabled,
    activeColor = activeColor
)
