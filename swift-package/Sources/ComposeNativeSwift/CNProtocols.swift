import Foundation
import SwiftUI

// MARK: - Color Bridge & Theme

public struct CNSwiftColor: Equatable, Sendable {
    public let red: Double
    public let green: Double
    public let blue: Double
    public let alpha: Double
    public let name: String?

    public init(red: Double, green: Double, blue: Double, alpha: Double = 1.0, name: String? = nil) {
        self.red = red
        self.green = green
        self.blue = blue
        self.alpha = alpha
        self.name = name
    }

    public init(hex: String, name: String? = nil) {
        var cleanHex = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()
        if cleanHex.hasPrefix("#") {
            cleanHex.removeFirst()
        }

        var rgb: UInt64 = 0
        Scanner(string: cleanHex).scanHexInt64(&rgb)

        if cleanHex.count == 6 {
            self.red = Double((rgb >> 16) & 0xFF) / 255.0
            self.green = Double((rgb >> 8) & 0xFF) / 255.0
            self.blue = Double(rgb & 0xFF) / 255.0
            self.alpha = 1.0
        } else if cleanHex.count == 8 {
            self.alpha = Double((rgb >> 24) & 0xFF) / 255.0
            self.red = Double((rgb >> 16) & 0xFF) / 255.0
            self.green = Double((rgb >> 8) & 0xFF) / 255.0
            self.blue = Double(rgb & 0xFF) / 255.0
        } else {
            self.red = 0
            self.green = 0
            self.blue = 0
            self.alpha = 1.0
        }
        self.name = name
    }

    public var swiftUIColor: SwiftUI.Color {
        SwiftUI.Color(.sRGB, red: red, green: green, blue: blue, opacity: alpha)
    }

    public static let primary = CNSwiftColor(hex: "#007AFF", name: "primary")
    public static let primaryContainer = CNSwiftColor(hex: "#DCE8FF", name: "primaryContainer")
    public static let secondary = CNSwiftColor(hex: "#5856D6", name: "secondary")
    public static let secondaryContainer = CNSwiftColor(hex: "#EBEAFF", name: "secondaryContainer")
    public static let tertiary = CNSwiftColor(hex: "#009688", name: "tertiary")
    public static let accent = CNSwiftColor(hex: "#FF9500", name: "accent")
    public static let surface = CNSwiftColor(hex: "#FFFFFF", name: "surface")
    public static let surfaceVariant = CNSwiftColor(hex: "#E5E5EA", name: "surfaceVariant")
    public static let background = CNSwiftColor(hex: "#F2F2F7", name: "background")
    public static let onSurface = CNSwiftColor(hex: "#1C1C1E", name: "onSurface")
    public static let onBackground = CNSwiftColor(hex: "#1C1C1E", name: "onBackground")
    public static let error = CNSwiftColor(hex: "#FF3B30", name: "error")
    public static let success = CNSwiftColor(hex: "#34C759", name: "success")
    public static let warning = CNSwiftColor(hex: "#FFCC00", name: "warning")
    public static let gray = CNSwiftColor(hex: "#8E8E93", name: "gray")
    public static let lightGray = CNSwiftColor(hex: "#D1D1D6", name: "lightGray")
    public static let darkGray = CNSwiftColor(hex: "#1C1C1E", name: "darkGray")
    public static let clear = CNSwiftColor(red: 0, green: 0, blue: 0, alpha: 0, name: "clear")
}

// MARK: - Material Theme Color Schemes (Light & Dark)

public struct CNSwiftColorScheme {
    public let isDark: Bool
    public let primary: CNSwiftColor
    public let onPrimary: CNSwiftColor
    public let primaryContainer: CNSwiftColor
    public let secondary: CNSwiftColor
    public let onSecondary: CNSwiftColor
    public let background: CNSwiftColor
    public let onBackground: CNSwiftColor
    public let surface: CNSwiftColor
    public let onSurface: CNSwiftColor
    public let surfaceVariant: CNSwiftColor
    public let error: CNSwiftColor
    public let success: CNSwiftColor
    public let outline: CNSwiftColor

    public static let light = CNSwiftColorScheme(
        isDark: false,
        primary: CNSwiftColor(hex: "#007AFF"),
        onPrimary: CNSwiftColor(hex: "#FFFFFF"),
        primaryContainer: CNSwiftColor(hex: "#DCE8FF"),
        secondary: CNSwiftColor(hex: "#5856D6"),
        onSecondary: CNSwiftColor(hex: "#FFFFFF"),
        background: CNSwiftColor(hex: "#F2F2F7"),
        onBackground: CNSwiftColor(hex: "#1C1C1E"),
        surface: CNSwiftColor(hex: "#FFFFFF"),
        onSurface: CNSwiftColor(hex: "#1C1C1E"),
        surfaceVariant: CNSwiftColor(hex: "#E5E5EA"),
        error: CNSwiftColor(hex: "#FF3B30"),
        success: CNSwiftColor(hex: "#34C759"),
        outline: CNSwiftColor(hex: "#C6C6C8")
    )

    public static let dark = CNSwiftColorScheme(
        isDark: true,
        primary: CNSwiftColor(hex: "#0A84FF"),
        onPrimary: CNSwiftColor(hex: "#002A5C"),
        primaryContainer: CNSwiftColor(hex: "#004085"),
        secondary: CNSwiftColor(hex: "#5E5CE6"),
        onSecondary: CNSwiftColor(hex: "#281B66"),
        background: CNSwiftColor(hex: "#000000"),
        onBackground: CNSwiftColor(hex: "#FFFFFF"),
        surface: CNSwiftColor(hex: "#1C1C1E"),
        onSurface: CNSwiftColor(hex: "#FFFFFF"),
        surfaceVariant: CNSwiftColor(hex: "#2C2C2E"),
        error: CNSwiftColor(hex: "#FF453A"),
        success: CNSwiftColor(hex: "#32D74B"),
        outline: CNSwiftColor(hex: "#48484A")
    )
}

public class CNSwiftThemeState: ObservableObject {
    @Published public var isDarkMode: Bool = false
    @Published public var customColorScheme: CNSwiftColorScheme?

    public static let shared = CNSwiftThemeState()

    public var currentColorScheme: CNSwiftColorScheme {
        if let custom = customColorScheme { return custom }
        return isDarkMode ? .dark : .light
    }
}

// MARK: - Shapes, Geometry & Materials

public enum CNSwiftShape: Equatable {
    case rectangle
    case circle
    case capsule
    case roundedCorner(radius: CGFloat)
    case customRounded(topStart: CGFloat, topEnd: CGFloat, bottomEnd: CGFloat, bottomStart: CGFloat)
}

public enum CNSwiftMaterialType: Equatable {
    case ultraThin
    case thin
    case regular
    case thick
    case ultraThick
}

public enum CNHapticType: Equatable {
    case light
    case medium
    case heavy
    case success
    case warning
    case error

    public func trigger() {
        #if os(iOS)
        switch self {
        case .light:
            UIImpactFeedbackGenerator(style: .light).impactOccurred()
        case .medium:
            UIImpactFeedbackGenerator(style: .medium).impactOccurred()
        case .heavy:
            UIImpactFeedbackGenerator(style: .heavy).impactOccurred()
        case .success:
            UINotificationFeedbackGenerator().notificationOccurred(.success)
        case .warning:
            UINotificationFeedbackGenerator().notificationOccurred(.warning)
        case .error:
            UINotificationFeedbackGenerator().notificationOccurred(.error)
        }
        #endif
    }
}

public struct CNSwiftPadding: Equatable {
    public var top: CGFloat
    public var leading: CGFloat
    public var bottom: CGFloat
    public var trailing: CGFloat

    public init(top: CGFloat = 0, leading: CGFloat = 0, bottom: CGFloat = 0, trailing: CGFloat = 0) {
        self.top = top
        self.leading = leading
        self.bottom = bottom
        self.trailing = trailing
    }

    public init(all: CGFloat) {
        self.init(top: all, leading: all, bottom: all, trailing: all)
    }

    public init(horizontal: CGFloat = 0, vertical: CGFloat = 0) {
        self.init(top: vertical, leading: horizontal, bottom: vertical, trailing: horizontal)
    }
}

public struct CNSwiftBorder: Equatable {
    public let width: CGFloat
    public let color: CNSwiftColor
    public let shape: CNSwiftShape

    public init(width: CGFloat = 1, color: CNSwiftColor = .gray, shape: CNSwiftShape = .rectangle) {
        self.width = width
        self.color = color
        self.shape = shape
    }
}

public struct CNSwiftShadow: Equatable {
    public let elevation: CGFloat
    public let color: CNSwiftColor
    public let radius: CGFloat
    public let x: CGFloat
    public let y: CGFloat

    public init(elevation: CGFloat = 2, color: CNSwiftColor = CNSwiftColor(red: 0, green: 0, blue: 0, alpha: 0.15), radius: CGFloat = 4, x: CGFloat = 0, y: CGFloat = 2) {
        self.elevation = elevation
        self.color = color
        self.radius = radius
        self.x = x
        self.y = y
    }
}

// MARK: - Typography

public enum CNSwiftFontWeight: Int, Equatable {
    case thin = 100
    case extraLight = 200
    case light = 300
    case regular = 400
    case medium = 500
    case semiBold = 600
    case bold = 700
    case extraBold = 800
    case black = 900

    public var swiftUIFontWeight: SwiftUI.Font.Weight {
        switch self {
        case .thin: return .thin
        case .extraLight: return .ultraLight
        case .light: return .light
        case .regular: return .regular
        case .medium: return .medium
        case .semiBold: return .semibold
        case .bold: return .bold
        case .extraBold: return .heavy
        case .black: return .black
        }
    }
}

public struct CNSwiftTextStyle: Equatable {
    public var color: CNSwiftColor
    public var fontSize: CGFloat
    public var fontWeight: CNSwiftFontWeight
    public var isItalic: Bool
    public var letterSpacing: CGFloat
    public var lineHeight: CGFloat?
    public var alignment: TextAlignment

    public init(
        color: CNSwiftColor = .onSurface,
        fontSize: CGFloat = 16,
        fontWeight: CNSwiftFontWeight = .regular,
        isItalic: Bool = false,
        letterSpacing: CGFloat = 0,
        lineHeight: CGFloat? = nil,
        alignment: TextAlignment = .leading
    ) {
        self.color = color
        self.fontSize = fontSize
        self.fontWeight = fontWeight
        self.isItalic = isItalic
        self.letterSpacing = letterSpacing
        self.lineHeight = lineHeight
        self.alignment = alignment
    }
}

public enum CNSwiftKeyboardType: Equatable {
    case `default`
    case email
    case number
    case phone
    case password
    case decimal
}

// MARK: - Modifier Models

public enum CNSwiftModifierElement {
    case padding(CNSwiftPadding)
    case frame(width: CGFloat?, height: CGFloat?, fillMaxWidth: Bool, fillMaxHeight: Bool)
    case background(color: CNSwiftColor, shape: CNSwiftShape)
    case clip(shape: CNSwiftShape)
    case cornerRadius(CGFloat)
    case border(CNSwiftBorder)
    case shadow(CNSwiftShadow)
    case opacity(Double)
    case offset(x: CGFloat, y: CGFloat)
    case aspectRatio(CGFloat)
    case weight(Float)
    case blur(CGFloat)
    case material(type: CNSwiftMaterialType, shape: CNSwiftShape)
    case haptic(type: CNHapticType)
    case refreshable(action: () -> Void)
    case searchable(query: String, onQueryChange: (String) -> Void, placeholder: String)
    case clickable(action: () -> Void)
}
