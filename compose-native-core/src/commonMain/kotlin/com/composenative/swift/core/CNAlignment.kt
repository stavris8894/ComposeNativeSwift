package com.composenative.swift.core

/**
 * Alignment in 2D space.
 */
sealed class CNAlignment {
    data object TopStart : CNAlignment()
    data object TopCenter : CNAlignment()
    data object TopEnd : CNAlignment()
    data object CenterStart : CNAlignment()
    data object Center : CNAlignment()
    data object CenterEnd : CNAlignment()
    data object BottomStart : CNAlignment()
    data object BottomCenter : CNAlignment()
    data object BottomEnd : CNAlignment()

    sealed class Horizontal : CNAlignment() {
        data object Start : Horizontal()
        data object CenterHorizontally : Horizontal()
        data object End : Horizontal()
    }

    sealed class Vertical : CNAlignment() {
        data object Top : Vertical()
        data object CenterVertically : Vertical()
        data object Bottom : Vertical()
    }

    companion object {
        val Top = Vertical.Top
        val CenterVertically = Vertical.CenterVertically
        val Bottom = Vertical.Bottom
        val Start = Horizontal.Start
        val CenterHorizontally = Horizontal.CenterHorizontally
        val End = Horizontal.End
    }
}

/**
 * Arrangement in layout containers (Column / Row).
 */
sealed class CNArrangement {
    data object Start : CNArrangement()
    data object End : CNArrangement()
    data object Top : CNArrangement()
    data object Bottom : CNArrangement()
    data object Center : CNArrangement()
    data object SpaceBetween : CNArrangement()
    data object SpaceAround : CNArrangement()
    data object SpaceEvenly : CNArrangement()
    data class SpacedBy(val space: CNDp, val alignment: CNAlignment? = null) : CNArrangement()

    companion object {
        val Start: CNArrangement = CNArrangement.Start
        val End: CNArrangement = CNArrangement.End
        val Top: CNArrangement = CNArrangement.Top
        val Bottom: CNArrangement = CNArrangement.Bottom
        val Center: CNArrangement = CNArrangement.Center
        val SpaceBetween: CNArrangement = CNArrangement.SpaceBetween
        val SpaceAround: CNArrangement = CNArrangement.SpaceAround
        val SpaceEvenly: CNArrangement = CNArrangement.SpaceEvenly

        fun spacedBy(space: CNDp): CNArrangement = SpacedBy(space)
        fun spacedBy(space: CNDp, alignment: CNAlignment): CNArrangement = SpacedBy(space, alignment)
    }
}

typealias Alignment = CNAlignment
typealias Arrangement = CNArrangement
typealias TextStyle = CNTextStyle
typealias FontWeight = CNFontWeight
typealias FontStyle = CNFontStyle
typealias TextAlign = CNTextAlign
typealias TextOverflow = CNTextOverflow
typealias KeyboardType = CNKeyboardType
typealias ButtonStyle = CNButtonStyle
typealias ContentScale = CNContentScale
typealias ImageSource = CNImageSource


/**
 * Content scale for images.
 */
enum class CNContentScale {
    Fit,
    Crop,
    FillBounds,
    Inside,
    None
}

/**
 * Text overflow strategy.
 */
enum class CNTextOverflow {
    Clip,
    Ellipsis,
    Visible
}

/**
 * Keyboard types for text input.
 */
enum class CNKeyboardType {
    Default,
    Ascii,
    Number,
    Phone,
    Uri,
    Email,
    Password,
    NumberPassword,
    Decimal
}

/**
 * Font weight matching Compose and SwiftUI.
 */
enum class CNFontWeight(val weightValue: Int) {
    Thin(100),
    ExtraLight(200),
    Light(300),
    Normal(400),
    Medium(500),
    SemiBold(600),
    Bold(700),
    ExtraBold(800),
    Black(900);

    companion object {
        val W100 = Thin
        val W200 = ExtraLight
        val W300 = Light
        val W400 = Normal
        val W500 = Medium
        val W600 = SemiBold
        val W700 = Bold
        val W800 = ExtraBold
        val W900 = Black
    }
}

/**
 * Font style (normal / italic).
 */
enum class CNFontStyle {
    Normal,
    Italic
}

/**
 * Text alignment.
 */
enum class CNTextAlign {
    Left,
    Right,
    Center,
    Justify,
    Start,
    End
}

/**
 * Text style configuration.
 */
data class CNTextStyle(
    val color: CNColor = CNColor.OnSurface,
    val fontSize: CNSp = 16.sp,
    val fontWeight: CNFontWeight = CNFontWeight.Normal,
    val fontStyle: CNFontStyle = CNFontStyle.Normal,
    val letterSpacing: CNSp = 0.sp,
    val lineHeight: CNSp = CNSp.Unspecified,
    val textAlign: CNTextAlign = CNTextAlign.Start
) {
    companion object {
        val Default = CNTextStyle()
        val H1 = CNTextStyle(fontSize = 32.sp, fontWeight = CNFontWeight.Bold, lineHeight = 38.sp)
        val H2 = CNTextStyle(fontSize = 28.sp, fontWeight = CNFontWeight.Bold, lineHeight = 34.sp)
        val H3 = CNTextStyle(fontSize = 24.sp, fontWeight = CNFontWeight.SemiBold, lineHeight = 28.sp)
        val H4 = CNTextStyle(fontSize = 20.sp, fontWeight = CNFontWeight.SemiBold, lineHeight = 24.sp)
        val H5 = CNTextStyle(fontSize = 18.sp, fontWeight = CNFontWeight.Medium, lineHeight = 22.sp)
        val H6 = CNTextStyle(fontSize = 16.sp, fontWeight = CNFontWeight.Medium, lineHeight = 20.sp)
        val BodyLarge = CNTextStyle(fontSize = 16.sp, fontWeight = CNFontWeight.Normal, lineHeight = 24.sp)
        val BodyMedium = CNTextStyle(fontSize = 14.sp, fontWeight = CNFontWeight.Normal, lineHeight = 20.sp)
        val BodySmall = CNTextStyle(fontSize = 12.sp, fontWeight = CNFontWeight.Normal, lineHeight = 16.sp)
        val LabelLarge = CNTextStyle(fontSize = 14.sp, fontWeight = CNFontWeight.Medium, lineHeight = 20.sp)
        val LabelMedium = CNTextStyle(fontSize = 12.sp, fontWeight = CNFontWeight.Medium, lineHeight = 16.sp)
        val LabelSmall = CNTextStyle(fontSize = 10.sp, fontWeight = CNFontWeight.Medium, lineHeight = 14.sp)
        val Button = CNTextStyle(fontSize = 16.sp, fontWeight = CNFontWeight.SemiBold)
        val Caption = CNTextStyle(fontSize = 12.sp, color = CNColor.Gray)
    }
}

/**
 * Button styles mapping to native SwiftUI & Compose buttons.
 */
enum class CNButtonStyle {
    Filled,
    Outlined,
    Text,
    Elevated,
    Tonal
}

/**
 * Image source (Asset, System Icon/SF Symbol, Remote URL).
 */
sealed class CNImageSource {
    data class Asset(val name: String) : CNImageSource()
    data class SystemIcon(val name: String) : CNImageSource()
    data class NetworkUrl(val url: String) : CNImageSource()
}
