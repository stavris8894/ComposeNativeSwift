import SwiftUI
import Combine

public protocol CNStateObservableScreen: AnyObject {
    func renderSwiftNode() -> CNRenderableNode
    func addStateListener(_ listener: @escaping () -> Void) -> () -> Void
    func onDispose()
}

public protocol CNStateObservableViewModel: AnyObject {
    func addStateListener(_ listener: @escaping () -> Void) -> () -> Void
    func onCleared()
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

public class CNViewModelObserver<VM: CNStateObservableViewModel>: ObservableObject {
    @Published public var version: Int = 0
    public let viewModel: VM
    private var unbind: (() -> Void)?

    public init(viewModel: VM) {
        self.viewModel = viewModel
        self.unbind = viewModel.addStateListener { [weak self] in
            DispatchQueue.main.async {
                self?.version += 1
            }
        }
    }

    deinit {
        unbind?()
        viewModel.onCleared()
    }
}

/**
 * The single developer entry point in SwiftUI.
 *
 * Usage:
 * ```swift
 * struct ContentView: View {
 *     var body: some View {
 *         // 1. Using a Screen with embedded ViewModels:
 *         ComposeNativeView(screen: ShowcaseScreen())
 *
 *         // 2. Or using a dedicated ViewModel:
 *         ComposeNativeViewModelView(viewModel: CounterViewModel()) { vm in
 *             // content
 *         }
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

/**
 * Renders a view directly bound to a Kotlin CNViewModel.
 */
public struct ComposeNativeViewModelView<VM: CNStateObservableViewModel>: View {
    @StateObject private var observer: CNViewModelObserver<VM>
    private let content: (VM) -> CNRenderableNode

    public init(viewModel: VM, content: @escaping (VM) -> CNRenderableNode) {
        _observer = StateObject(wrappedValue: CNViewModelObserver(viewModel: viewModel))
        self.content = content
    }

    public var body: some View {
        CNNodeRenderer(node: content(observer.viewModel))
    }
}
