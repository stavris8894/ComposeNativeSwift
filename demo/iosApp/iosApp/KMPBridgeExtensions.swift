import Foundation
import SwiftUI
import ComposeNativeSwift
import SharedApp

// MARK: - Native Bridge for Kotlin CNScreen & CNNode

class SwiftStateListener: NSObject, CNStateListener {
    private let callback: () -> Void

    init(callback: @escaping () -> Void) {
        self.callback = callback
    }

    func onStateChanged() {
        callback()
    }
}

extension CNScreen: @retroactive CNStateObservableScreen {
    public func renderSwiftNode() -> CNRenderableNode {
        let kotlinNode = self.render()
        return CNKotlinNodeBridge.convert(kotlinNode)
    }

    public func addStateListener(_ listener: @escaping () -> Void) -> () -> Void {
        let stateListener = SwiftStateListener(callback: listener)
        self.addListener(listener: stateListener)
        return { [weak self] in
            self?.removeListener(listener: stateListener)
        }
    }
}
