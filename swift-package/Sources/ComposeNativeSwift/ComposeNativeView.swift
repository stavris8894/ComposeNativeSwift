import SwiftUI
import Combine

public protocol CNStateObservableScreen: AnyObject {
    func renderSwiftNode() -> CNRenderableNode
    func addStateListener(_ listener: @escaping () -> Void) -> () -> Void
    func onDispose()
}

public class CNStateObserver: ObservableObject {
    @Published public var currentNode: CNRenderableNode
    private var unbind: (() -> Void)?

    public init(screen: CNStateObservableScreen) {
        self.currentNode = screen.renderSwiftNode()
        self.unbind = screen.addStateListener { [weak self, weak screen] in
            DispatchQueue.main.async {
                guard let self = self, let screen = screen else { return }
                self.currentNode = screen.renderSwiftNode()
            }
        }
    }

    public init(initialNode: CNRenderableNode) {
        self.currentNode = initialNode
    }

    deinit {
        unbind?()
    }
}

/**
 * The single developer entry point in SwiftUI.
 *
 * Usage:
 * ```swift
 * struct ContentView: View {
 *     var body: some View {
 *         ComposeNativeView(CounterScreen())
 *     }
 * }
 * ```
 */
public struct ComposeNativeView: View {
    @StateObject private var observer: CNStateObserver
    private let screen: CNStateObservableScreen?

    public init(screen: CNStateObservableScreen) {
        self.screen = screen
        _observer = StateObject(wrappedValue: CNStateObserver(screen: screen))
    }

    public init(node: CNRenderableNode) {
        self.screen = nil
        _observer = StateObject(wrappedValue: CNStateObserver(initialNode: node))
    }

    public var body: some View {
        CNNodeRenderer(node: observer.currentNode)
            .onDisappear {
                screen?.onDispose()
            }
    }
}
