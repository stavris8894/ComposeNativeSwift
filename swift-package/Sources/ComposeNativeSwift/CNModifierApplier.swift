import SwiftUI

public struct CNModifierApplier: ViewModifier {
    public let elements: [CNSwiftModifierElement]

    public init(elements: [CNSwiftModifierElement]) {
        self.elements = elements
    }

    public func body(content: Content) -> some View {
        elements.reduce(AnyView(content)) { currentView, element in
            apply(element: element, to: currentView)
        }
    }

    private func apply(element: CNSwiftModifierElement, to view: AnyView) -> AnyView {
        switch element {
        case .padding(let padding):
            return AnyView(
                view.padding(
                    EdgeInsets(
                        top: padding.top,
                        leading: padding.leading,
                        bottom: padding.bottom,
                        trailing: padding.trailing
                    )
                )
            )

        case .frame(let width, let height, let fillMaxWidth, let fillMaxHeight):
            return AnyView(
                view.frame(
                    minWidth: fillMaxWidth ? 0 : nil,
                    idealWidth: width,
                    maxWidth: fillMaxWidth ? .infinity : width,
                    minHeight: fillMaxHeight ? 0 : nil,
                    idealHeight: height,
                    maxHeight: fillMaxHeight ? .infinity : height
                )
            )

        case .background(let color, let shape):
            return AnyView(
                view.background(
                    applyShape(shape: shape)
                        .fill(color.swiftUIColor)
                )
            )

        case .clip(let shape):
            return AnyView(
                view.clipShape(applyShape(shape: shape))
            )

        case .cornerRadius(let radius):
            return AnyView(
                view.cornerRadius(radius)
            )

        case .border(let border):
            return AnyView(
                view.overlay(
                    applyShape(shape: border.shape)
                        .stroke(border.color.swiftUIColor, lineWidth: border.width)
                )
            )

        case .shadow(let shadow):
            return AnyView(
                view.shadow(
                    color: shadow.color.swiftUIColor,
                    radius: shadow.radius,
                    x: shadow.x,
                    y: shadow.y
                )
            )

        case .opacity(let alpha):
            return AnyView(
                view.opacity(alpha)
            )

        case .offset(let x, let y):
            return AnyView(
                view.offset(x: x, y: y)
            )

        case .aspectRatio(let ratio):
            return AnyView(
                view.aspectRatio(ratio, contentMode: .fit)
            )

        case .blur(let radius):
            return AnyView(
                view.blur(radius: radius)
            )

        case .material(let type, let shape):
            return AnyView(
                view.background(
                    applyMaterial(type: type)
                        .clipShape(applyShape(shape: shape))
                )
            )

        case .haptic(let type):
            return AnyView(
                view.onTapGesture {
                    type.trigger()
                }
            )

        case .refreshable(let action):
            return AnyView(
                view.refreshable {
                    action()
                }
            )

        case .searchable(let query, let onQueryChange, let placeholder):
            return AnyView(
                CNSearchableView(query: query, onQueryChange: onQueryChange, placeholder: placeholder, content: view)
            )

        case .clickable(let action):
            return AnyView(
                view.contentShape(Rectangle())
                    .onTapGesture {
                        action()
                    }
            )

        case .weight:
            return view
        }
    }

    private func applyShape(shape: CNSwiftShape) -> AnyShape {
        switch shape {
        case .rectangle:
            return AnyShape(Rectangle())
        case .circle:
            return AnyShape(Circle())
        case .capsule:
            return AnyShape(Capsule())
        case .roundedCorner(let radius):
            return AnyShape(RoundedRectangle(cornerRadius: radius))
        case .customRounded(let topStart, _, _, _):
            return AnyShape(RoundedRectangle(cornerRadius: topStart))
        }
    }

    private func applyMaterial(type: CNSwiftMaterialType) -> AnyView {
        #if os(iOS)
        switch type {
        case .ultraThin:
            return AnyView(Rectangle().fill(.ultraThinMaterial))
        case .thin:
            return AnyView(Rectangle().fill(.thinMaterial))
        case .regular:
            return AnyView(Rectangle().fill(.regularMaterial))
        case .thick:
            return AnyView(Rectangle().fill(.thickMaterial))
        case .ultraThick:
            return AnyView(Rectangle().fill(.ultraThickMaterial))
        }
        #else
        return AnyView(Rectangle().fill(Color.secondary.opacity(0.15)))
        #endif
    }
}

struct CNSearchableView: View {
    let query: String
    let onQueryChange: (String) -> Void
    let placeholder: String
    let content: AnyView
    @State private var text: String = ""

    var body: some View {
        content
            .searchable(text: $text, prompt: placeholder)
            .onAppear {
                text = query
            }
            .onChange(of: text) { newValue in
                onQueryChange(newValue)
            }
    }
}

public struct AnyShape: Shape {
    private let _path: @Sendable (CGRect) -> Path

    public init<S: Shape>(_ shape: S) {
        _path = { rect in
            shape.path(in: rect)
        }
    }

    public func path(in rect: CGRect) -> Path {
        _path(rect)
    }
}

public extension View {
    func applyComposeModifiers(_ elements: [CNSwiftModifierElement]) -> some View {
        modifier(CNModifierApplier(elements: elements))
    }
}
