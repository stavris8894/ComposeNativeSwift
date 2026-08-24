package com.composenative.swift.core

/**
 * Material 3 Color Scheme representation.
 * Supports dynamic token resolution for Light and Dark themes.
 */
data class CNColorScheme(
    val isDark: Boolean,
    val primary: CNColor,
    val onPrimary: CNColor,
    val primaryContainer: CNColor,
    val onPrimaryContainer: CNColor,
    val secondary: CNColor,
    val onSecondary: CNColor,
    val secondaryContainer: CNColor,
    val onSecondaryContainer: CNColor,
    val tertiary: CNColor,
    val onTertiary: CNColor,
    val tertiaryContainer: CNColor,
    val onTertiaryContainer: CNColor,
    val background: CNColor,
    val onBackground: CNColor,
    val surface: CNColor,
    val onSurface: CNColor,
    val surfaceVariant: CNColor,
    val onSurfaceVariant: CNColor,
    val surfaceContainer: CNColor,
    val surfaceContainerHigh: CNColor,
    val surfaceContainerLow: CNColor,
    val error: CNColor,
    val onError: CNColor,
    val errorContainer: CNColor,
    val onErrorContainer: CNColor,
    val outline: CNColor,
    val outlineVariant: CNColor,
    val scrim: CNColor
) {
    companion object {
        fun light(
            primary: CNColor = CNColor(0x00, 0x7A, 0xFF, name = "primary"), // iOS System Blue / Material Primary
            onPrimary: CNColor = CNColor(0xFF, 0xFF, 0xFF, name = "onPrimary"),
            primaryContainer: CNColor = CNColor(0xDC, 0xE8, 0xFF, name = "primaryContainer"),
            onPrimaryContainer: CNColor = CNColor(0x00, 0x1B, 0x3F, name = "onPrimaryContainer"),
            secondary: CNColor = CNColor(0x58, 0x56, 0xD6, name = "secondary"), // System Purple
            onSecondary: CNColor = CNColor(0xFF, 0xFF, 0xFF, name = "onSecondary"),
            secondaryContainer: CNColor = CNColor(0xEB, 0xEA, 0xFF, name = "secondaryContainer"),
            onSecondaryContainer: CNColor = CNColor(0x19, 0x10, 0x47, name = "onSecondaryContainer"),
            tertiary: CNColor = CNColor(0x00, 0x96, 0x88, name = "tertiary"), // Teal
            onTertiary: CNColor = CNColor(0xFF, 0xFF, 0xFF, name = "onTertiary"),
            tertiaryContainer: CNColor = CNColor(0xCE, 0xFA, 0xF1, name = "tertiaryContainer"),
            onTertiaryContainer: CNColor = CNColor(0x00, 0x20, 0x1B, name = "onTertiaryContainer"),
            background: CNColor = CNColor(0xF2, 0xF2, 0xF7, name = "background"),
            onBackground: CNColor = CNColor(0x1C, 0x1C, 0x1E, name = "onBackground"),
            surface: CNColor = CNColor(0xFF, 0xFF, 0xFF, name = "surface"),
            onSurface: CNColor = CNColor(0x1C, 0x1C, 0x1E, name = "onSurface"),
            surfaceVariant: CNColor = CNColor(0xE5, 0xE5, 0xEA, name = "surfaceVariant"),
            onSurfaceVariant: CNColor = CNColor(0x49, 0x45, 0x4F, name = "onSurfaceVariant"),
            surfaceContainer: CNColor = CNColor(0xF8, 0xF9, 0xFA, name = "surfaceContainer"),
            surfaceContainerHigh: CNColor = CNColor(0xEE, 0xF0, 0xF2, name = "surfaceContainerHigh"),
            surfaceContainerLow: CNColor = CNColor(0xFF, 0xFF, 0xFF, name = "surfaceContainerLow"),
            error: CNColor = CNColor(0xFF, 0x3B, 0x30, name = "error"),
            onError: CNColor = CNColor(0xFF, 0xFF, 0xFF, name = "onError"),
            errorContainer: CNColor = CNColor(0xFF, 0xDA, 0xD6, name = "errorContainer"),
            onErrorContainer: CNColor = CNColor(0x41, 0x00, 0x02, name = "onErrorContainer"),
            outline: CNColor = CNColor(0x79, 0x74, 0x7E, name = "outline"),
            outlineVariant: CNColor = CNColor(0xC4, 0xC7, 0xC5, name = "outlineVariant"),
            scrim: CNColor = CNColor(0x00, 0x00, 0x00, alpha = 0.4f, name = "scrim")
        ): CNColorScheme = CNColorScheme(
            isDark = false,
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            secondary = secondary,
            onSecondary = onSecondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = onSecondaryContainer,
            tertiary = tertiary,
            onTertiary = onTertiary,
            tertiaryContainer = tertiaryContainer,
            onTertiaryContainer = onTertiaryContainer,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            surfaceContainer = surfaceContainer,
            surfaceContainerHigh = surfaceContainerHigh,
            surfaceContainerLow = surfaceContainerLow,
            error = error,
            onError = onError,
            errorContainer = errorContainer,
            onErrorContainer = onErrorContainer,
            outline = outline,
            outlineVariant = outlineVariant,
            scrim = scrim
        )

        fun dark(
            primary: CNColor = CNColor(0x0A, 0x84, 0xFF, name = "primary"), // Dark Mode System Blue
            onPrimary: CNColor = CNColor(0x00, 0x2A, 0x5C, name = "onPrimary"),
            primaryContainer: CNColor = CNColor(0x00, 0x40, 0x85, name = "primaryContainer"),
            onPrimaryContainer: CNColor = CNColor(0xD1, 0xE4, 0xFF, name = "onPrimaryContainer"),
            secondary: CNColor = CNColor(0x5E, 0x5C, 0xE6, name = "secondary"), // Dark Mode Purple
            onSecondary: CNColor = CNColor(0x28, 0x1B, 0x66, name = "onSecondary"),
            secondaryContainer: CNColor = CNColor(0x3E, 0x30, 0x85, name = "secondaryContainer"),
            onSecondaryContainer: CNColor = CNColor(0xE4, 0xDF, 0xFF, name = "onSecondaryContainer"),
            tertiary: CNColor = CNColor(0x64, 0xD2, 0xFF, name = "tertiary"),
            onTertiary: CNColor = CNColor(0x00, 0x36, 0x40, name = "onTertiary"),
            tertiaryContainer: CNColor = CNColor(0x00, 0x4E, 0x5C, name = "tertiaryContainer"),
            onTertiaryContainer: CNColor = CNColor(0xBD, 0xEB, 0xFF, name = "onTertiaryContainer"),
            background: CNColor = CNColor(0x00, 0x00, 0x00, name = "background"), // Pure Dark / iOS Dark
            onBackground: CNColor = CNColor(0xFF, 0xFF, 0xFF, name = "onBackground"),
            surface: CNColor = CNColor(0x1C, 0x1C, 0x1E, name = "surface"), // iOS Elevated Surface Dark
            onSurface: CNColor = CNColor(0xFF, 0xFF, 0xFF, name = "onSurface"),
            surfaceVariant: CNColor = CNColor(0x2C, 0x2C, 0x2E, name = "surfaceVariant"),
            onSurfaceVariant: CNColor = CNColor(0x8E, 0x8E, 0x93, name = "onSurfaceVariant"),
            surfaceContainer: CNColor = CNColor(0x24, 0x24, 0x26, name = "surfaceContainer"),
            surfaceContainerHigh: CNColor = CNColor(0x3A, 0x3A, 0x3C, name = "surfaceContainerHigh"),
            surfaceContainerLow: CNColor = CNColor(0x16, 0x16, 0x18, name = "surfaceContainerLow"),
            error: CNColor = CNColor(0xFF, 0x45, 0x3A, name = "error"),
            onError: CNColor = CNColor(0x69, 0x00, 0x05, name = "onError"),
            errorContainer: CNColor = CNColor(0x93, 0x00, 0x0A, name = "errorContainer"),
            onErrorContainer: CNColor = CNColor(0xFF, 0xDA, 0xD6, name = "onErrorContainer"),
            outline: CNColor = CNColor(0x8E, 0x8E, 0x93, name = "outline"),
            outlineVariant: CNColor = CNColor(0x48, 0x48, 0x4A, name = "outlineVariant"),
            scrim: CNColor = CNColor(0x00, 0x00, 0x00, alpha = 0.7f, name = "scrim")
        ): CNColorScheme = CNColorScheme(
            isDark = true,
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            secondary = secondary,
            onSecondary = onSecondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = onSecondaryContainer,
            tertiary = tertiary,
            onTertiary = onTertiary,
            tertiaryContainer = tertiaryContainer,
            onTertiaryContainer = onTertiaryContainer,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            surfaceContainer = surfaceContainer,
            surfaceContainerHigh = surfaceContainerHigh,
            surfaceContainerLow = surfaceContainerLow,
            error = error,
            onError = onError,
            errorContainer = errorContainer,
            onErrorContainer = onErrorContainer,
            outline = outline,
            outlineVariant = outlineVariant,
            scrim = scrim
        )
    }
}

/**
 * Material 3 Typography system.
 */
data class CNTypography(
    val displayLarge: CNTextStyle = CNTextStyle(fontSize = 57.sp, lineHeight = 64.sp, fontWeight = CNFontWeight.Normal),
    val displayMedium: CNTextStyle = CNTextStyle(fontSize = 45.sp, lineHeight = 52.sp, fontWeight = CNFontWeight.Normal),
    val displaySmall: CNTextStyle = CNTextStyle(fontSize = 36.sp, lineHeight = 44.sp, fontWeight = CNFontWeight.Normal),
    val headlineLarge: CNTextStyle = CNTextStyle(fontSize = 32.sp, lineHeight = 40.sp, fontWeight = CNFontWeight.Bold),
    val headlineMedium: CNTextStyle = CNTextStyle(fontSize = 28.sp, lineHeight = 36.sp, fontWeight = CNFontWeight.Bold),
    val headlineSmall: CNTextStyle = CNTextStyle(fontSize = 24.sp, lineHeight = 32.sp, fontWeight = CNFontWeight.SemiBold),
    val titleLarge: CNTextStyle = CNTextStyle(fontSize = 22.sp, lineHeight = 28.sp, fontWeight = CNFontWeight.Bold),
    val titleMedium: CNTextStyle = CNTextStyle(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = CNFontWeight.SemiBold),
    val titleSmall: CNTextStyle = CNTextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = CNFontWeight.Medium),
    val bodyLarge: CNTextStyle = CNTextStyle(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = CNFontWeight.Normal),
    val bodyMedium: CNTextStyle = CNTextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = CNFontWeight.Normal),
    val bodySmall: CNTextStyle = CNTextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = CNFontWeight.Normal),
    val labelLarge: CNTextStyle = CNTextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = CNFontWeight.Medium),
    val labelMedium: CNTextStyle = CNTextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = CNFontWeight.Medium),
    val labelSmall: CNTextStyle = CNTextStyle(fontSize = 11.sp, lineHeight = 16.sp, fontWeight = CNFontWeight.Medium)
)

/**
 * Material 3 Shapes specification.
 */
data class CNShapes(
    val extraSmall: CNShape = CNShape.RoundedCorner(4.dp),
    val small: CNShape = CNShape.RoundedCorner(8.dp),
    val medium: CNShape = CNShape.RoundedCorner(12.dp),
    val large: CNShape = CNShape.RoundedCorner(16.dp),
    val extraLarge: CNShape = CNShape.RoundedCorner(28.dp)
)

/**
 * Theme container matching Compose MaterialTheme.
 */
data class CNThemeData(
    val colorScheme: CNColorScheme = CNColorScheme.light(),
    val typography: CNTypography = CNTypography(),
    val shapes: CNShapes = CNShapes()
)

object CNTheme {
    var current: CNThemeData = CNThemeData()
    
    val colorScheme: CNColorScheme get() = current.colorScheme
    val typography: CNTypography get() = current.typography
    val shapes: CNShapes get() = current.shapes
}

fun lightColorScheme(): CNColorScheme = CNColorScheme.light()
fun darkColorScheme(): CNColorScheme = CNColorScheme.dark()

typealias ColorScheme = CNColorScheme
typealias Typography = CNTypography
typealias Shapes = CNShapes
typealias MaterialTheme = CNTheme
