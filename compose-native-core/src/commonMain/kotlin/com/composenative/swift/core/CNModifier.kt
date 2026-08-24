package com.composenative.swift.core

/**
 * Modifier interface representing UI decor/layout transformations.
 * Fully compatible with Jetpack Compose Modifier chaining syntax.
 */
interface CNModifier {
    fun <R> foldIn(initial: R, operation: (R, CNModifier.Element) -> R): R
    fun <R> foldOut(initial: R, operation: (CNModifier.Element, R) -> R): R
    fun any(predicate: (CNModifier.Element) -> Boolean): Boolean
    fun all(predicate: (CNModifier.Element) -> Boolean): Boolean
    infix fun then(other: CNModifier): CNModifier =
        if (other === None) this else CombinedModifier(this, other)

    interface Element : CNModifier {
        override fun <R> foldIn(initial: R, operation: (R, Element) -> R): R =
            operation(initial, this)

        override fun <R> foldOut(initial: R, operation: (Element, R) -> R): R =
            operation(this, initial)

        override fun any(predicate: (Element) -> Boolean): Boolean = predicate(this)
        override fun all(predicate: (Element) -> Boolean): Boolean = predicate(this)
    }

    companion object None : CNModifier {
        override fun <R> foldIn(initial: R, operation: (R, Element) -> R): R = initial
        override fun <R> foldOut(initial: R, operation: (Element, R) -> R): R = initial
        override fun any(predicate: (Element) -> Boolean): Boolean = false
        override fun all(predicate: (Element) -> Boolean): Boolean = true
        override infix fun then(other: CNModifier): CNModifier = other
        override fun toString(): String = "CNModifier"
    }
}

class CombinedModifier(
    private val outer: CNModifier,
    private val inner: CNModifier
) : CNModifier {
    override fun <R> foldIn(initial: R, operation: (R, CNModifier.Element) -> R): R =
        inner.foldIn(outer.foldIn(initial, operation), operation)

    override fun <R> foldOut(initial: R, operation: (CNModifier.Element, R) -> R): R =
        outer.foldOut(inner.foldOut(initial, operation), operation)

    override fun any(predicate: (CNModifier.Element) -> Boolean): Boolean =
        outer.any(predicate) || inner.any(predicate)

    override fun all(predicate: (CNModifier.Element) -> Boolean): Boolean =
        outer.all(predicate) && inner.all(predicate)

    override fun toString(): String =
        "[" + foldIn("") { acc, element ->
            if (acc.isEmpty()) element.toString() else "$acc, $element"
        } + "]"
}

// -------------------------------------------------------------------------
// Materials & Vibrancy Types
// -------------------------------------------------------------------------

enum class CNMaterialType {
    UltraThin,
    Thin,
    Regular,
    Thick,
    UltraThick
}

enum class CNHapticType {
    Light,
    Medium,
    Heavy,
    Success,
    Warning,
    Error
}

data class CNSwipeAction(
    val title: String,
    val icon: String? = null,
    val color: CNColor = CNColor.Primary,
    val isDestructive: Boolean = false,
    val onClick: () -> Unit
)

// -------------------------------------------------------------------------
// Standard Modifier Elements
// -------------------------------------------------------------------------

data class CNPaddingModifier(val padding: CNPadding) : CNModifier.Element
data class CNWidthModifier(val width: CNDp) : CNModifier.Element
data class CNHeightModifier(val height: CNDp) : CNModifier.Element
data class CNSizeModifier(val width: CNDp, val height: CNDp) : CNModifier.Element
data class CNFillMaxWidthModifier(val fraction: Float = 1.0f) : CNModifier.Element
data class CNFillMaxHeightModifier(val fraction: Float = 1.0f) : CNModifier.Element
data class CNFillMaxSizeModifier(val fraction: Float = 1.0f) : CNModifier.Element
data class CNBackgroundModifier(val color: CNColor, val shape: CNShape = CNShape.Rectangle) : CNModifier.Element
data class CNClipModifier(val shape: CNShape) : CNModifier.Element
data class CNBorderModifier(val border: CNBorder) : CNModifier.Element
data class CNShadowModifier(val shadow: CNShadow) : CNModifier.Element
data class CNClickableModifier(val enabled: Boolean = true, val onClick: () -> Unit) : CNModifier.Element
data class CNAlphaModifier(val alpha: Float) : CNModifier.Element
data class CNWeightModifier(val weight: Float, val fill: Boolean = true) : CNModifier.Element
data class CNOffsetModifier(val x: CNDp = CNDp.Zero, val y: CNDp = CNDp.Zero) : CNModifier.Element
data class CNAspectRatioModifier(val ratio: Float, val matchHeightConstraintsFirst: Boolean = false) : CNModifier.Element
data class CNCornerRadiusModifier(val radius: CNDp) : CNModifier.Element
data class CNBlurModifier(val radius: CNDp) : CNModifier.Element
data class CNMaterialModifier(val material: CNMaterialType, val shape: CNShape = CNShape.Rectangle) : CNModifier.Element
data class CNHapticModifier(val type: CNHapticType) : CNModifier.Element
data class CNRefreshableModifier(val onRefresh: () -> Unit) : CNModifier.Element
data class CNSearchableModifier(val query: String, val onQueryChange: (String) -> Unit, val placeholder: String = "Search...") : CNModifier.Element
data class CNCustomTagModifier(val tag: String, val value: String) : CNModifier.Element

// -------------------------------------------------------------------------
// Modifier Extension Functions (Matching Jetpack Compose API)
// -------------------------------------------------------------------------

fun CNModifier.padding(all: CNDp): CNModifier =
    then(CNPaddingModifier(CNPadding(all)))

fun CNModifier.padding(horizontal: CNDp = CNDp.Zero, vertical: CNDp = CNDp.Zero): CNModifier =
    then(CNPaddingModifier(CNPadding(horizontal = horizontal, vertical = vertical)))

fun CNModifier.padding(
    start: CNDp = CNDp.Zero,
    top: CNDp = CNDp.Zero,
    end: CNDp = CNDp.Zero,
    bottom: CNDp = CNDp.Zero
): CNModifier =
    then(CNPaddingModifier(CNPadding(top = top, start = start, bottom = bottom, end = end)))

fun CNModifier.width(width: CNDp): CNModifier =
    then(CNWidthModifier(width))

fun CNModifier.height(height: CNDp): CNModifier =
    then(CNHeightModifier(height))

fun CNModifier.size(size: CNDp): CNModifier =
    then(CNSizeModifier(size, size))

fun CNModifier.size(width: CNDp, height: CNDp): CNModifier =
    then(CNSizeModifier(width, height))

fun CNModifier.fillMaxWidth(fraction: Float = 1.0f): CNModifier =
    then(CNFillMaxWidthModifier(fraction))

fun CNModifier.fillMaxHeight(fraction: Float = 1.0f): CNModifier =
    then(CNFillMaxHeightModifier(fraction))

fun CNModifier.fillMaxSize(fraction: Float = 1.0f): CNModifier =
    then(CNFillMaxSizeModifier(fraction))

fun CNModifier.background(color: CNColor, shape: CNShape = CNShape.Rectangle): CNModifier =
    then(CNBackgroundModifier(color, shape))

fun CNModifier.clip(shape: CNShape): CNModifier =
    then(CNClipModifier(shape))

fun CNModifier.cornerRadius(radius: CNDp): CNModifier =
    then(CNCornerRadiusModifier(radius))

fun CNModifier.border(width: CNDp, color: CNColor, shape: CNShape = CNShape.Rectangle): CNModifier =
    then(CNBorderModifier(CNBorder(width, color, shape)))

fun CNModifier.border(border: CNBorder): CNModifier =
    then(CNBorderModifier(border))

fun CNModifier.shadow(
    elevation: CNDp,
    shape: CNShape = CNShape.Rectangle,
    clip: Boolean = elevation > CNDp.Zero,
    ambientColor: CNColor = CNColor.Black.copyWithAlpha(0.1f),
    spotColor: CNColor = CNColor.Black.copyWithAlpha(0.2f)
): CNModifier =
    then(CNShadowModifier(CNShadow(elevation = elevation, radius = elevation * 1.5f, shape = shape)))

fun CNModifier.clickable(enabled: Boolean = true, onClick: () -> Unit): CNModifier =
    then(CNClickableModifier(enabled, onClick))

fun CNModifier.alpha(alpha: Float): CNModifier =
    then(CNAlphaModifier(alpha))

fun CNModifier.weight(weight: Float, fill: Boolean = true): CNModifier =
    then(CNWeightModifier(weight, fill))

fun CNModifier.offset(x: CNDp = CNDp.Zero, y: CNDp = CNDp.Zero): CNModifier =
    then(CNOffsetModifier(x, y))

fun CNModifier.aspectRatio(ratio: Float, matchHeightConstraintsFirst: Boolean = false): CNModifier =
    then(CNAspectRatioModifier(ratio, matchHeightConstraintsFirst))

fun CNModifier.blur(radius: CNDp): CNModifier =
    then(CNBlurModifier(radius))

fun CNModifier.material(material: CNMaterialType, shape: CNShape = CNShape.Rectangle): CNModifier =
    then(CNMaterialModifier(material, shape))

fun CNModifier.haptic(type: CNHapticType): CNModifier =
    then(CNHapticModifier(type))

fun CNModifier.refreshable(onRefresh: () -> Unit): CNModifier =
    then(CNRefreshableModifier(onRefresh))

fun CNModifier.searchable(query: String, onQueryChange: (String) -> Unit, placeholder: String = "Search..."): CNModifier =
    then(CNSearchableModifier(query, onQueryChange, placeholder))

fun CNModifier.tag(tag: String, value: String): CNModifier =
    then(CNCustomTagModifier(tag, value))

/**
 * Drop-in alias matching Jetpack Compose Modifier
 */
typealias Modifier = CNModifier
