package com.composenative.swift.navigation

import com.composenative.swift.core.*

enum class CNLiquidGlassStyle {
    UltraThin,
    FrostedPrism,
    Luminescent,
    TranslucentDark,
    MirrorChrome
}

/**
 * Liquid Glass styling properties for iOS Navigation and UI elements.
 * Generates futuristic translucent blur with specular edge highlights.
 */
data class CNLiquidGlassProperties(
    val style: CNLiquidGlassStyle = CNLiquidGlassStyle.UltraThin,
    val blurRadius: CNDp = 20.dp,
    val tint: CNColor = CNColor.White.copyWithAlpha(0.12f),
    val borderHighlight: CNColor = CNColor.White.copyWithAlpha(0.35f),
    val cornerRadius: CNDp = 24.dp,
    val specularOpacity: Float = 0.45f
)

data class CNLiquidGlassModifier(val properties: CNLiquidGlassProperties) : CNModifier.Element

/**
 * Liquid Glass modifier applying dynamic frosted glass with specular highlight border.
 */
fun CNModifier.liquidGlass(
    style: CNLiquidGlassStyle = CNLiquidGlassStyle.UltraThin,
    blurRadius: CNDp = 20.dp,
    tint: CNColor = CNColor.White.copyWithAlpha(0.12f),
    borderHighlight: CNColor = CNColor.White.copyWithAlpha(0.35f),
    cornerRadius: CNDp = 24.dp,
    specularOpacity: Float = 0.45f
): CNModifier = then(
    CNLiquidGlassModifier(
        CNLiquidGlassProperties(
            style = style,
            blurRadius = blurRadius,
            tint = tint,
            borderHighlight = borderHighlight,
            cornerRadius = cornerRadius,
            specularOpacity = specularOpacity
        )
    )
)
