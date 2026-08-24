import SwiftUI

// MARK: - Swift Navigation Nodes

public final class CNSwiftNavHostNode: CNSwiftBaseNode {
    public let activeRoute: String
    public let backStackCount: Int
    public let currentTitle: String
    public let navBarStyle: String
    public let showBackButton: Bool
    public let onPopBack: () -> Void
    public let content: CNRenderableNode

    public init(
        id: String = UUID().uuidString,
        activeRoute: String,
        backStackCount: Int,
        currentTitle: String = "",
        navBarStyle: String = "LiquidGlass",
        showBackButton: Bool = false,
        onPopBack: @escaping () -> Void = {},
        content: CNRenderableNode,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.activeRoute = activeRoute
        self.backStackCount = backStackCount
        self.currentTitle = currentTitle
        self.navBarStyle = navBarStyle
        self.showBackButton = showBackButton
        self.onPopBack = onPopBack
        self.content = content
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftLiquidGlassNode: CNSwiftBaseNode {
    public let properties: CNSwiftLiquidGlassProperties
    public let content: CNRenderableNode

    public init(
        id: String = UUID().uuidString,
        properties: CNSwiftLiquidGlassProperties = CNSwiftLiquidGlassProperties(),
        content: CNRenderableNode,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.properties = properties
        self.content = content
        super.init(id: id, modifiers: modifiers)
    }
}

// MARK: - Liquid Glass Navigation Bar (iOS 16+ / iOS 26+ Futuristic Style)

public struct CNLiquidGlassNavBar: View {
    @ObservedObject private var themeState = CNSwiftThemeState.shared
    public let title: String
    public let showBackButton: Bool
    public let onBack: () -> Void

    public init(title: String, showBackButton: Bool = false, onBack: @escaping () -> Void = {}) {
        self.title = title
        self.showBackButton = showBackButton
        self.onBack = onBack
    }

    public var body: some View {
        HStack(spacing: 12) {
            if showBackButton {
                Button(action: {
                    CNHapticType.light.trigger()
                    onBack()
                }) {
                    HStack(spacing: 4) {
                        Image(systemName: "chevron.left")
                            .font(.system(size: 16, weight: .bold))
                        Text("Back")
                            .font(.system(size: 16, weight: .medium))
                    }
                    .foregroundColor(themeState.isDarkMode ? .white : .primary)
                    .padding(.horizontal, 10)
                    .padding(.vertical, 6)
                    .background(
                        Capsule()
                            .fill(themeState.isDarkMode ? Color.white.opacity(0.15) : Color.black.opacity(0.06))
                    )
                }
            }

            Text(title)
                .font(.system(size: 18, weight: .bold))
                .foregroundColor(themeState.isDarkMode ? .white : .primary)
                .lineLimit(1)

            Spacer()
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 12)
        .background(
            ZStack {
                #if os(iOS)
                Rectangle()
                    .fill(.ultraThinMaterial)
                #else
                Color.black.opacity(0.2)
                #endif

                // Specular Edge Reflection Gradient
                LinearGradient(
                    gradient: Gradient(colors: [
                        Color.white.opacity(themeState.isDarkMode ? 0.25 : 0.4),
                        Color.clear,
                        Color.white.opacity(themeState.isDarkMode ? 0.05 : 0.1)
                    ]),
                    startPoint: .topLeading,
                    endPoint: .bottomTrailing
                )
            }
            .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
            .overlay(
                RoundedRectangle(cornerRadius: 18, style: .continuous)
                    .stroke(
                        LinearGradient(
                            colors: [
                                Color.white.opacity(0.5),
                                Color.white.opacity(0.1),
                                Color.clear
                            ],
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        ),
                        lineWidth: 1
                    )
            )
            .shadow(color: Color.black.opacity(0.12), radius: 10, x: 0, y: 5)
        )
        .padding(.horizontal, 12)
        .padding(.top, 4)
    }
}

// MARK: - Liquid Glass Container View

public struct CNLiquidGlassContainer: View {
    @ObservedObject private var themeState = CNSwiftThemeState.shared
    public let properties: CNSwiftLiquidGlassProperties
    public let content: AnyView

    public init(properties: CNSwiftLiquidGlassProperties = CNSwiftLiquidGlassProperties(), content: AnyView) {
        self.properties = properties
        self.content = content
    }

    public var body: some View {
        content
            .background(
                ZStack {
                    #if os(iOS)
                    RoundedRectangle(cornerRadius: properties.cornerRadius, style: .continuous)
                        .fill(.ultraThinMaterial)
                    #else
                    RoundedRectangle(cornerRadius: properties.cornerRadius)
                        .fill(Color.gray.opacity(0.2))
                    #endif

                    // Translucent Tint
                    properties.tint.swiftUIColor
                        .clipShape(RoundedRectangle(cornerRadius: properties.cornerRadius, style: .continuous))

                    // Specular Highlight
                    LinearGradient(
                        colors: [
                            Color.white.opacity(properties.specularOpacity),
                            Color.white.opacity(0.05),
                            Color.clear
                        ],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    )
                    .clipShape(RoundedRectangle(cornerRadius: properties.cornerRadius, style: .continuous))
                }
                .overlay(
                    RoundedRectangle(cornerRadius: properties.cornerRadius, style: .continuous)
                        .stroke(
                            LinearGradient(
                                colors: [
                                    properties.borderHighlight.swiftUIColor,
                                    Color.white.opacity(0.1),
                                    Color.clear
                                ],
                                startPoint: .topLeading,
                                endPoint: .bottomTrailing
                            ),
                            lineWidth: 1.2
                        )
                )
                .shadow(color: Color.black.opacity(0.15), radius: 12, x: 0, y: 6)
            )
    }
}
