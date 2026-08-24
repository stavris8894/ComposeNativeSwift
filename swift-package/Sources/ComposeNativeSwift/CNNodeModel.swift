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

public final class CNSwiftDatePickerNode: CNSwiftBaseNode {
    public let title: String
    public var timestampMs: Double
    public let onDateChange: (Double) -> Void
    public let isEnabled: Bool

    public init(
        id: String = UUID().uuidString,
        title: String = "Select Date",
        timestampMs: Double = Date().timeIntervalSince1970 * 1000,
        onDateChange: @escaping (Double) -> Void,
        isEnabled: Bool = true,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.title = title
        self.timestampMs = timestampMs
        self.onDateChange = onDateChange
        self.isEnabled = isEnabled
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftStepperNode: CNSwiftBaseNode {
    public var value: Double
    public let onValueChange: (Double) -> Void
    public let min: Double
    public let max: Double
    public let step: Double
    public let label: String
    public let isEnabled: Bool

    public init(
        id: String = UUID().uuidString,
        value: Double,
        onValueChange: @escaping (Double) -> Void,
        min: Double = 0,
        max: Double = 100,
        step: Double = 1,
        label: String = "",
        isEnabled: Bool = true,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.value = value
        self.onValueChange = onValueChange
        self.min = min
        self.max = max
        self.step = step
        self.label = label
        self.isEnabled = isEnabled
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftRatingBarNode: CNSwiftBaseNode {
    public var rating: Int
    public let maxRating: Int
    public let onRatingChange: (Int) -> Void
    public let isEnabled: Bool
    public let activeColor: CNSwiftColor

    public init(
        id: String = UUID().uuidString,
        rating: Int,
        maxRating: Int = 5,
        onRatingChange: @escaping (Int) -> Void,
        isEnabled: Bool = true,
        activeColor: CNSwiftColor = .accent,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.rating = rating
        self.maxRating = maxRating
        self.onRatingChange = onRatingChange
        self.isEnabled = isEnabled
        self.activeColor = activeColor
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftMenuItem: Identifiable {
    public let id: String
    public let title: String
    public let icon: String?
    public let isDestructive: Bool
    public let isEnabled: Bool
    public let onClick: () -> Void

    public init(id: String = UUID().uuidString, title: String, icon: String? = nil, isDestructive: Bool = false, isEnabled: Bool = true, onClick: @escaping () -> Void) {
        self.id = id
        self.title = title
        self.icon = icon
        self.isDestructive = isDestructive
        self.isEnabled = isEnabled
        self.onClick = onClick
    }
}

public final class CNSwiftMenuNode: CNSwiftBaseNode {
    public let title: String
    public let items: [CNSwiftMenuItem]
    public let triggerContent: CNRenderableNode?

    public init(
        id: String = UUID().uuidString,
        title: String = "Menu",
        items: [CNSwiftMenuItem] = [],
        triggerContent: CNRenderableNode? = nil,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.title = title
        self.items = items
        self.triggerContent = triggerContent
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftPagerNode: CNSwiftBaseNode {
    public let isHorizontal: Bool
    public var currentPage: Int
    public let onPageChange: (Int) -> Void
    public let children: [CNRenderableNode]

    public init(
        id: String = UUID().uuidString,
        isHorizontal: Bool = true,
        currentPage: Int = 0,
        onPageChange: @escaping (Int) -> Void = { _ in },
        children: [CNRenderableNode] = [],
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.isHorizontal = isHorizontal
        self.currentPage = currentPage
        self.onPageChange = onPageChange
        self.children = children
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftSearchBarNode: CNSwiftBaseNode {
    public var query: String
    public let onQueryChange: (String) -> Void
    public let placeholder: String
    public let onSearch: (String) -> Void

    public init(
        id: String = UUID().uuidString,
        query: String,
        onQueryChange: @escaping (String) -> Void,
        placeholder: String = "Search...",
        onSearch: @escaping (String) -> Void = { _ in },
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.query = query
        self.onQueryChange = onQueryChange
        self.placeholder = placeholder
        self.onSearch = onSearch
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftSnackbarNode: CNSwiftBaseNode {
    public let message: String
    public let actionLabel: String?
    public let onAction: (() -> Void)?

    public init(
        id: String = UUID().uuidString,
        message: String,
        actionLabel: String? = nil,
        onAction: (() -> Void)? = nil,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.message = message
        self.actionLabel = actionLabel
        self.onAction = onAction
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

public final class CNSwiftRangeSliderNode: CNSwiftBaseNode {
    public var startValue: Float
    public var endValue: Float
    public let onValuesChange: (Float, Float) -> Void
    public let min: Float
    public let max: Float
    public let step: Float
    public let isEnabled: Bool
    public let activeColor: CNSwiftColor

    public init(
        id: String = UUID().uuidString,
        startValue: Float = 0.2,
        endValue: Float = 0.8,
        onValuesChange: @escaping (Float, Float) -> Void,
        min: Float = 0,
        max: Float = 1,
        step: Float = 0,
        isEnabled: Bool = true,
        activeColor: CNSwiftColor = .primary,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.startValue = startValue
        self.endValue = endValue
        self.onValuesChange = onValuesChange
        self.min = min
        self.max = max
        self.step = step
        self.isEnabled = isEnabled
        self.activeColor = activeColor
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftRadioButtonNode: CNSwiftBaseNode {
    public let isSelected: Bool
    public let onClick: () -> Void
    public let label: String?
    public let isEnabled: Bool
    public let selectedColor: CNSwiftColor

    public init(
        id: String = UUID().uuidString,
        isSelected: Bool,
        onClick: @escaping () -> Void,
        label: String? = nil,
        isEnabled: Bool = true,
        selectedColor: CNSwiftColor = .primary,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.isSelected = isSelected
        self.onClick = onClick
        self.label = label
        self.isEnabled = isEnabled
        self.selectedColor = selectedColor
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftChipNode: CNSwiftBaseNode {
    public let text: String
    public let isSelected: Bool
    public let onClick: () -> Void
    public let icon: String?
    public let isEnabled: Bool

    public init(
        id: String = UUID().uuidString,
        text: String,
        isSelected: Bool = false,
        onClick: @escaping () -> Void,
        icon: String? = nil,
        isEnabled: Bool = true,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.text = text
        self.isSelected = isSelected
        self.onClick = onClick
        self.icon = icon
        self.isEnabled = isEnabled
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftSegmentedButtonNode: CNSwiftBaseNode {
    public let items: [String]
    public let selectedIndex: Int
    public let onSelectIndex: (Int) -> Void

    public init(
        id: String = UUID().uuidString,
        items: [String],
        selectedIndex: Int,
        onSelectIndex: @escaping (Int) -> Void,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.items = items
        self.selectedIndex = selectedIndex
        self.onSelectIndex = onSelectIndex
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftListItemNode: CNSwiftBaseNode {
    public let headline: CNRenderableNode
    public let supporting: CNRenderableNode?
    public let leading: CNRenderableNode?
    public let trailing: CNRenderableNode?
    public let onClick: (() -> Void)?

    public init(
        id: String = UUID().uuidString,
        headline: CNRenderableNode,
        supporting: CNRenderableNode? = nil,
        leading: CNRenderableNode? = nil,
        trailing: CNRenderableNode? = nil,
        onClick: (() -> Void)? = nil,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.headline = headline
        self.supporting = supporting
        self.leading = leading
        self.trailing = trailing
        self.onClick = onClick
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftAccordionNode: CNSwiftBaseNode {
    public let title: String
    public var isExpanded: Bool
    public let onToggle: (Bool) -> Void
    public let content: CNRenderableNode

    public init(
        id: String = UUID().uuidString,
        title: String,
        isExpanded: Bool = false,
        onToggle: @escaping (Bool) -> Void,
        content: CNRenderableNode,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.title = title
        self.isExpanded = isExpanded
        self.onToggle = onToggle
        self.content = content
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftBannerNode: CNSwiftBaseNode {
    public let text: String
    public let primaryActionText: String?
    public let onPrimaryAction: (() -> Void)?
    public let secondaryActionText: String?
    public let onSecondaryAction: (() -> Void)?

    public init(
        id: String = UUID().uuidString,
        text: String,
        primaryActionText: String? = nil,
        onPrimaryAction: (() -> Void)? = nil,
        secondaryActionText: String? = nil,
        onSecondaryAction: (() -> Void)? = nil,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.text = text
        self.primaryActionText = primaryActionText
        self.onPrimaryAction = onPrimaryAction
        self.secondaryActionText = secondaryActionText
        self.onSecondaryAction = onSecondaryAction
        super.init(id: id, modifiers: modifiers)
    }
}

public final class CNSwiftTabRowNode: CNSwiftBaseNode {
    public let tabs: [String]
    public let selectedIndex: Int
    public let onTabSelected: (Int) -> Void

    public init(
        id: String = UUID().uuidString,
        tabs: [String],
        selectedIndex: Int,
        onTabSelected: @escaping (Int) -> Void,
        modifiers: [CNSwiftModifierElement] = []
    ) {
        self.tabs = tabs
        self.selectedIndex = selectedIndex
        self.onTabSelected = onTabSelected
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

public final class CNSwiftLazyGridNode: CNSwiftBaseNode {
    public let columnsCount: Int
    public let spacing: CGFloat
    public let contentPadding: CNSwiftPadding
    public let children: [CNRenderableNode]

    public init(id: String = UUID().uuidString, columnsCount: Int = 2, spacing: CGFloat = 8, contentPadding: CNSwiftPadding = CNSwiftPadding(), children: [CNRenderableNode] = [], modifiers: [CNSwiftModifierElement] = []) {
        self.columnsCount = columnsCount
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
