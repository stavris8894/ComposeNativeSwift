import Foundation
import SwiftUI

public protocol CNRenderableNode: AnyObject {
    var id: String { get }
    var modifierElements: [CNSwiftModifierElement] { get }
}

// MARK: - Swift Node Definitions

open class CNSwiftBaseNode: CNRenderableNode {
    public let id: String
    public var modifierElements: [CNSwiftModifierElement]

    public init(id: String = UUID().uuidString, modifiers: [CNSwiftModifierElement] = []) {
        self.id = id
        self.modifierElements = modifiers
    }
}

public final class CNSwiftTextNode: CNSwiftBaseNode {
    public let text: String
    public let style: CNSwiftTextStyle
    public let maxLines: Int?

    public init(id: String = UUID().uuidString, text: String, style: CNSwiftTextStyle = CNSwiftTextStyle(), maxLines: Int? = nil, modifiers: [CNSwiftModifierElement] = []) {
        self.text = text
        self.style = style
        self.maxLines = maxLines
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftButtonNode: CNSwiftBaseNode {
    public let onClick: () -> Void
    public let isEnabled: Bool
    public let content: CNRenderableNode

    public init(id: String = UUID().uuidString, onClick: @escaping () -> Void, isEnabled: Bool = true, content: CNRenderableNode, modifiers: [CNSwiftModifierElement] = []) {
        self.onClick = onClick
        self.isEnabled = isEnabled
        self.content = content
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftColumnNode: CNSwiftBaseNode {
    public let spacing: CGFloat
    public let horizontalAlignment: HorizontalAlignment
    public let children: [CNRenderableNode]

    public init(id: String = UUID().uuidString, spacing: CGFloat = 0, horizontalAlignment: HorizontalAlignment = .leading, children: [CNRenderableNode] = [], modifiers: [CNSwiftModifierElement] = []) {
        self.spacing = spacing
        self.horizontalAlignment = horizontalAlignment
        self.children = children
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftRowNode: CNSwiftBaseNode {
    public let spacing: CGFloat
    public let verticalAlignment: VerticalAlignment
    public let children: [CNRenderableNode]

    public init(id: String = UUID().uuidString, spacing: CGFloat = 0, verticalAlignment: VerticalAlignment = .center, children: [CNRenderableNode] = [], modifiers: [CNSwiftModifierElement] = []) {
        self.spacing = spacing
        self.verticalAlignment = verticalAlignment
        self.children = children
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftBoxNode: CNSwiftBaseNode {
    public let alignment: SwiftUI.Alignment
    public let children: [CNRenderableNode]

    public init(id: String = UUID().uuidString, alignment: SwiftUI.Alignment = .topLeading, children: [CNRenderableNode] = [], modifiers: [CNSwiftModifierElement] = []) {
        self.alignment = alignment
        self.children = children
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftTextFieldNode: CNSwiftBaseNode {
    public var value: String
    public let onValueChange: (String) -> Void
    public let placeholder: String
    public let isSecure: Bool
    public let keyboardType: CNSwiftKeyboardType
    public let isEnabled: Bool
    public let isError: Bool

    public init(
        id: String = UUID().uuidString,
        value: String,
        onValueChange: @escaping (String) -> Void,
        placeholder: String = "",
        isSecure: Bool = false,
        keyboardType: CNSwiftKeyboardType = .default,
        isEnabled: Bool = true,
        isError: Bool = false,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.value = value
        self.onValueChange = onValueChange
        self.placeholder = placeholder
        self.isSecure = isSecure
        self.keyboardType = keyboardType
        self.isEnabled = isEnabled
        self.isError = isError
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftSwitchNode: CNSwiftBaseNode {
    public var isChecked: Bool
    public let onCheckedChange: (Bool) -> Void
    public let isEnabled: Bool
    public let tint: CNSwiftColor

    public init(id: String = UUID().uuidString, isChecked: Bool, onCheckedChange: @escaping (Bool) -> Void, isEnabled: Bool = true, tint: CNSwiftColor = .primary, modifiers: [CNSwiftModifierElement] = []) {
        self.isChecked = isChecked
        self.onCheckedChange = onCheckedChange
        self.isEnabled = isEnabled
        self.tint = tint
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftSliderNode: CNSwiftBaseNode {
    public var value: Float
    public let onValueChange: (Float) -> Void
    public let min: Float
    public let max: Float
    public let step: Float
    public let isEnabled: Bool
    public let activeColor: CNSwiftColor

    public init(id: String = UUID().uuidString, value: Float, onValueChange: @escaping (Float) -> Void, min: Float = 0, max: Float = 1, step: Float = 0, isEnabled: Bool = true, activeColor: CNSwiftColor = .primary, modifiers: [CNSwiftModifierElement] = []) {
        self.value = value
        self.onValueChange = onValueChange
        self.min = min
        self.max = max
        self.step = step
        self.isEnabled = isEnabled
        self.activeColor = activeColor
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftLazyListNode: CNSwiftBaseNode {
    public let isVertical: Bool
    public let spacing: CGFloat
    public let contentPadding: CNSwiftPadding
    public let children: [CNRenderableNode]

    public init(id: String = UUID().uuidString, isVertical: Bool = true, spacing: CGFloat = 0, contentPadding: CNSwiftPadding = CNSwiftPadding(), children: [CNRenderableNode] = [], modifiers: [CNSwiftModifierElement] = []) {
        self.isVertical = isVertical
        self.spacing = spacing
        self.contentPadding = contentPadding
        self.children = children
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftImageNode: CNSwiftBaseNode {
    public enum ImageType {
        case sfSymbol(String)
        case asset(String)
        case remote(URL)
    }

    public let source: ImageType
    public let contentDescription: String?
    public let contentMode: ContentMode
    public let tint: CNSwiftColor?

    public init(id: String = UUID().uuidString, source: ImageType, contentDescription: String? = nil, contentMode: ContentMode = .fit, tint: CNSwiftColor? = nil, modifiers: [CNSwiftModifierElement] = []) {
        self.source = source
        self.contentDescription = contentDescription
        self.contentMode = contentMode
        self.tint = tint
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftCardNode: CNSwiftBaseNode {
    public let shape: CNSwiftShape
    public let elevation: CGFloat
    public let border: CNSwiftBorder?
    public let backgroundColor: CNSwiftColor
    public let content: CNRenderableNode

    public init(id: String = UUID().uuidString, shape: CNSwiftShape = .roundedCorner(radius: 12), elevation: CGFloat = 2, border: CNSwiftBorder? = nil, backgroundColor: CNSwiftColor = .surface, content: CNRenderableNode, modifiers: [CNSwiftModifierElement] = []) {
        self.shape = shape
        self.elevation = elevation
        self.border = border
        self.backgroundColor = backgroundColor
        self.content = content
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftScaffoldNode: CNSwiftBaseNode {
    public let topBarTitle: String?
    public let content: CNRenderableNode

    public init(id: String = UUID().uuidString, topBarTitle: String? = nil, content: CNRenderableNode, modifiers: [CNSwiftModifierElement] = []) {
        self.topBarTitle = topBarTitle
        self.content = content
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftProgressNode: CNSwiftBaseNode {
    public let isCircular: Bool
    public let progress: Float?
    public let color: CNSwiftColor

    public init(id: String = UUID().uuidString, isCircular: Bool = true, progress: Float? = nil, color: CNSwiftColor = .primary, modifiers: [CNSwiftModifierElement] = []) {
        self.isCircular = isCircular
        self.progress = progress
        self.color = color
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftBadgeNode: CNSwiftBaseNode {
    public let text: String?
    public let backgroundColor: CNSwiftColor
    public let contentColor: CNSwiftColor

    public init(id: String = UUID().uuidString, text: String?, backgroundColor: CNSwiftColor = .error, contentColor: CNSwiftColor = .surface, modifiers: [CNSwiftModifierElement] = []) {
        self.text = text
        self.backgroundColor = backgroundColor
        self.contentColor = contentColor
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftSpacerNode: CNSwiftBaseNode {}

public final class CNSwiftDividerNode: CNSwiftBaseNode {
    public let color: CNSwiftColor
    public let thickness: CGFloat

    public init(id: String = UUID().uuidString, color: CNSwiftColor = .gray, thickness: CGFloat = 1, modifiers: [CNSwiftModifierElement] = []) {
        self.color = color
        self.thickness = thickness
        super.init(id: id, modifiers: modifiers)
    }
}
