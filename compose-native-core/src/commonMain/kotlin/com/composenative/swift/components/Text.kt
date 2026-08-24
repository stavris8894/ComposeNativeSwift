package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Text composable matching Jetpack Compose Text.
 */
fun Text(
    text: String,
    modifier: CNModifier = CNModifier.None,
    color: CNColor = CNColor.OnSurface,
    fontSize: CNSp = 16.sp,
    fontWeight: CNFontWeight = CNFontWeight.Normal,
    fontStyle: CNFontStyle = CNFontStyle.Normal,
    letterSpacing: CNSp = 0.sp,
    lineHeight: CNSp = CNSp.Unspecified,
    textAlign: CNTextAlign = CNTextAlign.Start,
    maxLines: Int? = null,
    overflow: CNTextOverflow = CNTextOverflow.Clip,
    style: CNTextStyle? = null
): CNTextNode {
    val finalStyle = (style ?: CNTextStyle.Default).copy(
        color = if (color != CNColor.OnSurface) color else (style?.color ?: CNColor.OnSurface),
        fontSize = if (fontSize != 16.sp) fontSize else (style?.fontSize ?: 16.sp),
        fontWeight = if (fontWeight != CNFontWeight.Normal) fontWeight else (style?.fontWeight ?: CNFontWeight.Normal),
        fontStyle = if (fontStyle != CNFontStyle.Normal) fontStyle else (style?.fontStyle ?: CNFontStyle.Normal),
        letterSpacing = if (letterSpacing != 0.sp) letterSpacing else (style?.letterSpacing ?: 0.sp),
        lineHeight = if (lineHeight != CNSp.Unspecified) lineHeight else (style?.lineHeight ?: CNSp.Unspecified),
        textAlign = if (textAlign != CNTextAlign.Start) textAlign else (style?.textAlign ?: CNTextAlign.Start)
    )

    return CNTextNode(
        modifier = modifier,
        text = text,
        style = finalStyle,
        maxLines = maxLines,
        overflow = overflow
    )
}
