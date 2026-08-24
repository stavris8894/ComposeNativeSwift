package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Circular progress indicator matching Jetpack Compose CircularProgressIndicator.
 */
fun CircularProgressIndicator(
    modifier: CNModifier = CNModifier.None,
    progress: Float? = null,
    color: CNColor = CNColor.Primary,
    strokeWidth: CNDp = 4.dp
): CNCircularProgressIndicatorNode = CNCircularProgressIndicatorNode(
    modifier = modifier,
    progress = progress,
    color = color,
    strokeWidth = strokeWidth
)

/**
 * Linear progress indicator matching Jetpack Compose LinearProgressIndicator.
 */
fun LinearProgressIndicator(
    modifier: CNModifier = CNModifier.None,
    progress: Float? = null,
    color: CNColor = CNColor.Primary,
    trackColor: CNColor = CNColor.SurfaceVariant
): CNLinearProgressIndicatorNode = CNLinearProgressIndicatorNode(
    modifier = modifier,
    progress = progress,
    color = color,
    trackColor = trackColor
)

/**
 * Badge matching Jetpack Compose Badge / BadgedBox.
 */
fun Badge(
    modifier: CNModifier = CNModifier.None,
    text: String? = null,
    backgroundColor: CNColor = CNColor.Error,
    contentColor: CNColor = CNColor.White,
    content: (() -> CNNode)? = null
): CNBadgeNode = CNBadgeNode(
    modifier = modifier,
    text = text,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    content = content?.invoke()
)
