import SwiftUI
import ComposeNativeSwift

/**
 * Clean SwiftUI host view.
 * All ViewModels, reactive states, and business logic live 100% in Kotlin Multiplatform!
 */
struct ContentView: View {
    @StateObject private var themeState = CNSwiftThemeState.shared
    @Environment(\.colorScheme) private var systemColorScheme

    // Shared screen containing all ViewModels in Common Kotlin
    private let showcaseScreen = ShowcaseScreen()

    var body: some View {
        ComposeNativeView(screen: showcaseScreen)
            .preferredColorScheme(themeState.isDarkMode ? .dark : .light)
            .onAppear {
                themeState.isDarkMode = (systemColorScheme == .dark)
            }
            .ignoresSafeArea(.keyboard)
    }
}
