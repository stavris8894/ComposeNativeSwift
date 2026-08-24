package com.composenative.swift.core

import kotlin.jvm.JvmInline

/**
 * Density-independent pixel unit matching Compose Dp and SwiftUI points.
 */
@JvmInline
value class CNDp(val value: Float) {
    operator fun plus(other: CNDp): CNDp = CNDp(value + other.value)
    operator fun minus(other: CNDp): CNDp = CNDp(value - other.value)
    operator fun times(scalar: Float): CNDp = CNDp(value * scalar)
    operator fun div(scalar: Float): CNDp = CNDp(value / scalar)
    operator fun compareTo(other: CNDp): Int = value.compareTo(other.value)

    companion object {
        val Zero = CNDp(0f)
        val Hairline = CNDp(0.5f)
        val Unspecified = CNDp(Float.NaN)
    }
}

val Int.dp: CNDp get() = CNDp(this.toFloat())
val Float.dp: CNDp get() = CNDp(this)
val Double.dp: CNDp get() = CNDp(this.toFloat())

/**
 * Scale-independent pixel unit matching Compose Sp and SwiftUI Font points.
 */
@JvmInline
value class CNSp(val value: Float) {
    operator fun plus(other: CNSp): CNSp = CNSp(value + other.value)
    operator fun minus(other: CNSp): CNSp = CNSp(value - other.value)
    operator fun times(scalar: Float): CNSp = CNSp(value * scalar)
    operator fun div(scalar: Float): CNSp = CNSp(value / scalar)
    operator fun compareTo(other: CNSp): Int = value.compareTo(other.value)

    companion object {
        val Unspecified = CNSp(Float.NaN)
    }
}

val Int.sp: CNSp get() = CNSp(this.toFloat())
val Float.sp: CNSp get() = CNSp(this)
val Double.sp: CNSp get() = CNSp(this.toFloat())

/**
 * Padding specification for components.
 */
data class CNPadding(
    val top: CNDp = CNDp.Zero,
    val start: CNDp = CNDp.Zero,
    val bottom: CNDp = CNDp.Zero,
    val end: CNDp = CNDp.Zero
) {
    constructor(all: CNDp) : this(top = all, start = all, bottom = all, end = all)
    constructor(horizontal: CNDp = CNDp.Zero, vertical: CNDp = CNDp.Zero) : this(
        top = vertical,
        start = horizontal,
        bottom = vertical,
        end = horizontal
    )

    companion object {
        val Zero = CNPadding(CNDp.Zero)
    }
}

/**
 * Shapes supported across Compose and SwiftUI.
 */
sealed class CNShape {
    data object Rectangle : CNShape()
    data object Circle : CNShape()
    data object Capsule : CNShape()
    data class RoundedCorner(
        val topStart: CNDp = CNDp.Zero,
        val topEnd: CNDp = CNDp.Zero,
        val bottomEnd: CNDp = CNDp.Zero,
        val bottomStart: CNDp = CNDp.Zero
    ) : CNShape() {
        constructor(radius: CNDp) : this(topStart = radius, topEnd = radius, bottomEnd = radius, bottomStart = radius)
    }

    companion object {
        fun rounded(radius: CNDp) = RoundedCorner(radius)
    }
}

/**
 * Border specification.
 */
data class CNBorder(
    val width: CNDp = 1.dp,
    val color: CNColor = CNColor.Divider,
    val shape: CNShape = CNShape.Rectangle
)

/**
 * Shadow specification.
 */
data class CNShadow(
    val elevation: CNDp = 2.dp,
    val color: CNColor = CNColor.Black.copyWithAlpha(0.15f),
    val radius: CNDp = 4.dp,
    val x: CNDp = 0.dp,
    val y: CNDp = 2.dp,
    val shape: CNShape = CNShape.Rectangle
)

typealias Dp = CNDp
typealias Sp = CNSp
typealias Padding = CNPadding
typealias Shape = CNShape
typealias Border = CNBorder
typealias Shadow = CNShadow

