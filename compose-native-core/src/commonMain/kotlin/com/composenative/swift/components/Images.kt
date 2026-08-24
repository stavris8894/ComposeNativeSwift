package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Image composable matching Jetpack Compose Image.
 */
fun Image(
    source: CNImageSource,
    contentDescription: String? = null,
    modifier: CNModifier = CNModifier.None,
    contentScale: CNContentScale = CNContentScale.Fit,
    tint: CNColor? = null
): CNImageNode = CNImageNode(
    modifier = modifier,
    source = source,
    contentDescription = contentDescription,
    contentScale = contentScale,
    tint = tint
)

/**
 * Async Image composable loading from remote URL.
 */
fun AsyncImage(
    url: String,
    contentDescription: String? = null,
    modifier: CNModifier = CNModifier.None,
    contentScale: CNContentScale = CNContentScale.Crop
): CNImageNode = CNImageNode(
    modifier = modifier,
    source = CNImageSource.NetworkUrl(url),
    contentDescription = contentDescription,
    contentScale = contentScale
)

/**
 * Icon composable matching Jetpack Compose Icon.
 */
fun Icon(
    icon: String,
    contentDescription: String? = null,
    modifier: CNModifier = CNModifier.None,
    tint: CNColor = CNColor.Current,
    size: CNDp = 24.dp
): CNIconNode = CNIconNode(
    modifier = modifier,
    icon = icon,
    contentDescription = contentDescription,
    tint = tint,
    size = size
)

/**
 * Common System Icons (Cross-mapped between SF Symbols and Material Icons).
 */
object CNIcons {
    const val Add = "plus"
    const val Check = "checkmark"
    const val Close = "xmark"
    const val Settings = "gearshape"
    const val Home = "house"
    const val Person = "person.circle"
    const val Favorite = "heart.fill"
    const val FavoriteBorder = "heart"
    const val Search = "magnifyingglass"
    const val Share = "square.and.arrow.up"
    const val Star = "star.fill"
    const val Trash = "trash"
    const val ArrowBack = "chevron.left"
    const val ArrowForward = "chevron.right"
    const val Bell = "bell"
    const val Lock = "lock"
    const val Mail = "envelope"
    const val Eye = "eye"
    const val EyeSlash = "eye.slash"
}
