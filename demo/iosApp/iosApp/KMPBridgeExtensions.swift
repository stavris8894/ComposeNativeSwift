import Foundation
import SwiftUI
import ComposeNativeSwift

/**
 * Adapter converting Kotlin CNScreen to Swift CNStateObservableScreen.
 * In a full KMP project with SharedApp.framework linked, this bridges
 * Kotlin CNScreen directly to SwiftUI ComposeNativeView.
 */
public class KotlinScreenBridgeAdapter: CNStateObservableScreen {
    private let renderBlock: () -> CNRenderableNode
    private var stateListeners: [() -> Void] = []
    private var onDisposeBlock: (() -> Void)?

    public init(
        render: @escaping () -> CNRenderableNode,
        onDispose: (() -> Void)? = nil
    ) {
        self.renderBlock = render
        self.onDisposeBlock = onDispose
    }

    public func renderSwiftNode() -> CNRenderableNode {
        return renderBlock()
    }

    public func addStateListener(_ listener: @escaping () -> Void) -> () -> Void {
        stateListeners.append(listener)
        return { [weak self] in
            self?.stateListeners.removeAll()
        }
    }

    public func notifyStateChanged() {
        stateListeners.forEach { $0() }
    }

    public func onDispose() {
        stateListeners.removeAll()
        onDisposeBlock?()
    }
}
