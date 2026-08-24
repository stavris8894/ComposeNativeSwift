import SwiftUI

public struct CNNodeRenderer: View {
    public let node: CNRenderableNode

    public init(node: CNRenderableNode) {
        self.node = node
    }

    public var body: some View {
        renderNode(node)
            .applyComposeModifiers(node.modifierElements)
    }

    @ViewBuilder
    private func renderNode(_ node: CNRenderableNode) -> some View {
        switch node {
        case let textNode as CNSwiftTextNode:
            renderText(textNode)

        case let buttonNode as CNSwiftButtonNode:
            renderButton(buttonNode)

        case let columnNode as CNSwiftColumnNode:
            renderColumn(columnNode)

        case let rowNode as CNSwiftRowNode:
            renderRow(rowNode)

        case let boxNode as CNSwiftBoxNode:
            renderBox(boxNode)

        case let textFieldNode as CNSwiftTextFieldNode:
            renderTextField(textFieldNode)

        case let switchNode as CNSwiftSwitchNode:
            renderSwitch(switchNode)

        case let sliderNode as CNSwiftSliderNode:
            renderSlider(sliderNode)

        case let lazyListNode as CNSwiftLazyListNode:
            renderLazyList(lazyListNode)

        case let imageNode as CNSwiftImageNode:
            renderImage(imageNode)

        case let cardNode as CNSwiftCardNode:
            renderCard(cardNode)

        case let scaffoldNode as CNSwiftScaffoldNode:
            renderScaffold(scaffoldNode)

        case let progressNode as CNSwiftProgressNode:
            renderProgress(progressNode)

        case let badgeNode as CNSwiftBadgeNode:
            renderBadge(badgeNode)

        case _ as CNSwiftSpacerNode:
            Spacer()

        case let dividerNode as CNSwiftDividerNode:
            Divider()
                .overlay(dividerNode.color.swiftUIColor)
                .frame(height: dividerNode.thickness)

        default:
            EmptyView()
        }
    }

    // MARK: - Component Renderers

    @ViewBuilder
    private func renderText(_ node: CNSwiftTextNode) -> some View {
        if node.style.isItalic {
            Text(node.text)
                .font(.system(size: node.style.fontSize, weight: node.style.fontWeight.swiftUIFontWeight))
                .foregroundColor(node.style.color.swiftUIColor)
                .italic()
                .multilineTextAlignment(node.style.alignment)
                .lineLimit(node.maxLines)
        } else {
            Text(node.text)
                .font(.system(size: node.style.fontSize, weight: node.style.fontWeight.swiftUIFontWeight))
                .foregroundColor(node.style.color.swiftUIColor)
                .multilineTextAlignment(node.style.alignment)
                .lineLimit(node.maxLines)
        }
    }

    @ViewBuilder
    private func renderButton(_ node: CNSwiftButtonNode) -> some View {
        Button(action: node.onClick) {
            CNNodeRenderer(node: node.content)
        }
        .disabled(!node.isEnabled)
    }

    @ViewBuilder
    private func renderColumn(_ node: CNSwiftColumnNode) -> some View {
        VStack(alignment: node.horizontalAlignment, spacing: node.spacing) {
            ForEach(node.children, id: \.id) { child in
                CNNodeRenderer(node: child)
            }
        }
    }

    @ViewBuilder
    private func renderRow(_ node: CNSwiftRowNode) -> some View {
        HStack(alignment: node.verticalAlignment, spacing: node.spacing) {
            ForEach(node.children, id: \.id) { child in
                CNNodeRenderer(node: child)
            }
        }
    }

    @ViewBuilder
    private func renderBox(_ node: CNSwiftBoxNode) -> some View {
        ZStack(alignment: node.alignment) {
            ForEach(node.children, id: \.id) { child in
                CNNodeRenderer(node: child)
            }
        }
    }

    @ViewBuilder
    private func renderTextField(_ node: CNSwiftTextFieldNode) -> some View {
        CNTextFieldView(node: node)
    }

    @ViewBuilder
    private func renderSwitch(_ node: CNSwiftSwitchNode) -> some View {
        CNSwitchView(node: node)
    }

    @ViewBuilder
    private func renderSlider(_ node: CNSwiftSliderNode) -> some View {
        CNSliderView(node: node)
    }

    @ViewBuilder
    private func renderLazyList(_ node: CNSwiftLazyListNode) -> some View {
        if node.isVertical {
            ScrollView(.vertical, showsIndicators: true) {
                LazyVStack(spacing: node.spacing) {
                    ForEach(node.children, id: \.id) { child in
                        CNNodeRenderer(node: child)
                    }
                }
                .padding(EdgeInsets(
                    top: node.contentPadding.top,
                    leading: node.contentPadding.leading,
                    bottom: node.contentPadding.bottom,
                    trailing: node.contentPadding.trailing
                ))
            }
        } else {
            ScrollView(.horizontal, showsIndicators: true) {
                LazyHStack(spacing: node.spacing) {
                    ForEach(node.children, id: \.id) { child in
                        CNNodeRenderer(node: child)
                    }
                }
                .padding(EdgeInsets(
                    top: node.contentPadding.top,
                    leading: node.contentPadding.leading,
                    bottom: node.contentPadding.bottom,
                    trailing: node.contentPadding.trailing
                ))
            }
        }
    }

    @ViewBuilder
    private func renderImage(_ node: CNSwiftImageNode) -> some View {
        switch node.source {
        case .sfSymbol(let systemName):
            Image(systemName: systemName)
                .resizable()
                .aspectRatio(contentMode: node.contentMode)
                .foregroundColor(node.tint?.swiftUIColor)

        case .asset(let name):
            Image(name)
                .resizable()
                .aspectRatio(contentMode: node.contentMode)
                .foregroundColor(node.tint?.swiftUIColor)

        case .remote(let url):
            AsyncImage(url: url) { phase in
                switch phase {
                case .empty:
                    ProgressView()
                case .success(let image):
                    image.resizable().aspectRatio(contentMode: node.contentMode)
                case .failure:
                    Image(systemName: "photo")
                        .foregroundColor(.gray)
                @unknown default:
                    EmptyView()
                }
            }
        }
    }

    @ViewBuilder
    private func renderCard(_ node: CNSwiftCardNode) -> some View {
        CNNodeRenderer(node: node.content)
            .background(node.backgroundColor.swiftUIColor)
            .clipShape(RoundedRectangle(cornerRadius: 12))
            .shadow(color: Color.black.opacity(0.1), radius: node.elevation, x: 0, y: node.elevation / 2)
    }

    @ViewBuilder
    private func renderScaffold(_ node: CNSwiftScaffoldNode) -> some View {
        NavigationStack {
            CNNodeRenderer(node: node.content)
                .navigationTitle(node.topBarTitle ?? "")
                #if os(iOS)
                .navigationBarTitleDisplayMode(.inline)
                #endif
        }
    }

    @ViewBuilder
    private func renderProgress(_ node: CNSwiftProgressNode) -> some View {
        if node.isCircular {
            if let p = node.progress {
                ProgressView(value: Double(p))
                    .progressViewStyle(CircularProgressViewStyle(tint: node.color.swiftUIColor))
            } else {
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: node.color.swiftUIColor))
            }
        } else {
            if let p = node.progress {
                ProgressView(value: Double(p))
                    .tint(node.color.swiftUIColor)
            } else {
                ProgressView()
                    .tint(node.color.swiftUIColor)
            }
        }
    }

    @ViewBuilder
    private func renderBadge(_ node: CNSwiftBadgeNode) -> some View {
        if let text = node.text {
            Text(text)
                .font(.system(size: 11, weight: .bold))
                .foregroundColor(node.contentColor.swiftUIColor)
                .padding(.horizontal, 8)
                .padding(.vertical, 4)
                .background(node.backgroundColor.swiftUIColor)
                .clipShape(Capsule())
        }
    }
}

// MARK: - Stateful Control Views

struct CNTextFieldView: View {
    let node: CNSwiftTextFieldNode
    @State private var text: String = ""

    var body: some View {
        let field = Group {
            if node.isSecure {
                SecureField(node.placeholder, text: $text)
            } else {
                TextField(node.placeholder, text: $text)
            }
        }
        .disabled(!node.isEnabled)
        .padding(12)
        .background(Color.secondary.opacity(0.08))
        .cornerRadius(8)
        .overlay(
            RoundedRectangle(cornerRadius: 8)
                .stroke(node.isError ? Color.red : Color.gray.opacity(0.4), lineWidth: 1)
        )
        .onAppear {
            text = node.value
        }
        .onChange(of: text) { newValue in
            node.onValueChange(newValue)
        }

        #if os(iOS)
        switch node.keyboardType {
        case .email:
            field.keyboardType(.emailAddress)
        case .number:
            field.keyboardType(.numberPad)
        case .phone:
            field.keyboardType(.phonePad)
        case .decimal:
            field.keyboardType(.decimalPad)
        default:
            field.keyboardType(.default)
        }
        #else
        field
        #endif
    }
}

struct CNSwitchView: View {
    let node: CNSwiftSwitchNode
    @State private var isOn: Bool = false

    var body: some View {
        Toggle("", isOn: $isOn)
            .labelsHidden()
            .tint(node.tint.swiftUIColor)
            .disabled(!node.isEnabled)
            .onAppear {
                isOn = node.isChecked
            }
            .onChange(of: isOn) { newValue in
                node.onCheckedChange(newValue)
            }
    }
}

struct CNSliderView: View {
    let node: CNSwiftSliderNode
    @State private var value: Float = 0

    var body: some View {
        Group {
            if node.step > 0 {
                Slider(
                    value: $value,
                    in: node.min...node.max,
                    step: node.step
                )
            } else {
                Slider(
                    value: $value,
                    in: node.min...node.max
                )
            }
        }
        .tint(node.activeColor.swiftUIColor)
        .disabled(!node.isEnabled)
        .onAppear {
            value = node.value
        }
        .onChange(of: value) { newValue in
            node.onValueChange(newValue)
        }
    }
}
