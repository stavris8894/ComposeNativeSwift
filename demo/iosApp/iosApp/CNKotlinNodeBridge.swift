import Foundation
import SwiftUI
import ComposeNativeSwift
import SharedApp

public class CNKotlinNodeBridge {
    public static func convert(_ node: CNNode) -> CNRenderableNode {
        let modifiers = convertModifiers(node.modifierElements)

        switch node {
        case let textNode as CNTextNode:
            return CNSwiftTextNode(
                id: textNode.id,
                text: textNode.text,
                style: convertTextStyle(textNode.style),
                maxLines: textNode.maxLines?.intValue,
                modifiers: modifiers
            )

        case let btnNode as CNButtonNode:
            return CNSwiftButtonNode(
                id: btnNode.id,
                onClick: { btnNode.onClick() },
                isEnabled: btnNode.enabled,
                content: convert(btnNode.content),
                modifiers: modifiers
            )

        case let colNode as CNColumnNode:
            return CNSwiftColumnNode(
                id: colNode.id,
                spacing: CGFloat(colNode.verticalArrangement.description.contains("spacedBy") ? 8 : 0),
                horizontalAlignment: convertHorizontalAlignment(colNode.horizontalAlignment),
                children: colNode.children.map { convert($0) },
                modifiers: modifiers
            )

        case let rowNode as CNRowNode:
            return CNSwiftRowNode(
                id: rowNode.id,
                spacing: 8,
                verticalAlignment: .center,
                children: rowNode.children.map { convert($0) },
                modifiers: modifiers
            )

        case let boxNode as CNBoxNode:
            return CNSwiftBoxNode(
                id: boxNode.id,
                alignment: .center,
                children: boxNode.children.map { convert($0) },
                modifiers: modifiers
            )

        case let cardNode as CNCardNode:
            return CNSwiftCardNode(
                id: cardNode.id,
                elevation: CGFloat(cardNode.elevation),
                backgroundColor: convertColor(cardNode.backgroundColor),
                content: convert(cardNode.content),
                modifiers: modifiers
            )

        case let surfNode as CNSurfaceNode:
            return CNSwiftCardNode(
                id: surfNode.id,
                elevation: CGFloat(surfNode.elevation),
                backgroundColor: convertColor(surfNode.color),
                content: convert(surfNode.content),
                modifiers: modifiers
            )

        case let scaffoldNode as CNScaffoldNode:
            return CNSwiftScaffoldNode(
                id: scaffoldNode.id,
                topBarTitle: scaffoldNode.topBar?.title,
                content: convert(scaffoldNode.content),
                modifiers: modifiers
            )

        case let navHostNode as CNNavHostNode:
            return CNSwiftNavHostNode(
                id: navHostNode.id,
                activeRoute: navHostNode.activeRoute,
                backStackCount: Int(navHostNode.backStackCount),
                currentTitle: navHostNode.currentTitle,
                navBarStyle: navHostNode.navBarStyle,
                showBackButton: navHostNode.showBackButton,
                onPopBack: { navHostNode.onPopBack() },
                content: convert(navHostNode.content),
                modifiers: modifiers
            )

        case let glassNode as CNLiquidGlassNode:
            return CNSwiftLiquidGlassNode(
                id: glassNode.id,
                properties: CNSwiftLiquidGlassProperties(
                    blurRadius: CGFloat(glassNode.blurRadius),
                    cornerRadius: CGFloat(glassNode.cornerRadius)
                ),
                content: convert(glassNode.content),
                modifiers: modifiers
            )

        case let lazyCol as CNLazyColumnNode:
            return CNSwiftLazyListNode(
                id: lazyCol.id,
                isVertical: true,
                spacing: 12,
                contentPadding: CNSwiftPadding(horizontal: CGFloat(lazyCol.contentPadding.start), vertical: CGFloat(lazyCol.contentPadding.top)),
                children: lazyCol.children.map { convert($0) },
                modifiers: modifiers
            )

        case let lazyRow as CNLazyRowNode:
            return CNSwiftLazyListNode(
                id: lazyRow.id,
                isVertical: false,
                spacing: 12,
                contentPadding: CNSwiftPadding(all: CGFloat(lazyRow.contentPadding.start)),
                children: lazyRow.children.map { convert($0) },
                modifiers: modifiers
            )

        case let tfNode as CNTextFieldNode:
            return CNSwiftTextFieldNode(
                id: tfNode.id,
                value: tfNode.value,
                onValueChange: { tfNode.onValueChange($0) },
                placeholder: tfNode.placeholder,
                isSecure: tfNode.isSecure,
                keyboardType: convertKeyboardType(tfNode.keyboardType),
                modifiers: modifiers
            )

        case let switchNode as CNSwitchNode:
            return CNSwiftSwitchNode(
                id: switchNode.id,
                isChecked: switchNode.checked,
                onCheckedChange: { switchNode.onCheckedChange(KotlinBoolean(value: $0)) },
                modifiers: modifiers
            )

        case let sliderNode as CNSliderNode:
            return CNSwiftSliderNode(
                id: sliderNode.id,
                value: sliderNode.value,
                onValueChange: { sliderNode.onValueChange(KotlinFloat(value: $0)) },
                modifiers: modifiers
            )

        case let badgeNode as CNBadgeNode:
            return CNSwiftBadgeNode(
                id: badgeNode.id,
                text: badgeNode.text,
                backgroundColor: convertColor(badgeNode.backgroundColor),
                contentColor: convertColor(badgeNode.contentColor),
                modifiers: modifiers
            )

        case let iconNode as CNIconNode:
            return CNSwiftImageNode(
                id: iconNode.id,
                source: .sfSymbol(iconNode.icon),
                tint: convertColor(iconNode.tint),
                modifiers: modifiers
            )

        case let imgNode as CNImageNode:
            return CNSwiftImageNode(
                id: imgNode.id,
                source: convertImageSource(imgNode.source),
                modifiers: modifiers
            )

        case let spacerNode as CNSpacerNode:
            return CNSwiftSpacerNode(id: spacerNode.id, modifiers: modifiers)

        case let divNode as CNDividerNode:
            return CNSwiftDividerNode(
                id: divNode.id,
                color: convertColor(divNode.color),
                thickness: CGFloat(divNode.thickness),
                modifiers: modifiers
            )

        case let dpNode as CNDatePickerNode:
            return CNSwiftDatePickerNode(
                id: dpNode.id,
                title: dpNode.title,
                timestampMs: Double(dpNode.timestampMs),
                onDateChange: { dpNode.onDateChange(KotlinLong(value: Int64($0))) },
                modifiers: modifiers
            )

        case let stepperNode as CNStepperNode:
            let minVal = (stepperNode.range.start as? NSNumber)?.doubleValue ?? 0
            let maxVal = (stepperNode.range.endInclusive as? NSNumber)?.doubleValue ?? 100
            return CNSwiftStepperNode(
                id: stepperNode.id,
                value: stepperNode.value,
                onValueChange: { stepperNode.onValueChange(KotlinDouble(value: $0)) },
                min: minVal,
                max: maxVal,
                step: stepperNode.step,
                label: stepperNode.label,
                modifiers: modifiers
            )

        case let ratingNode as CNRatingBarNode:
            return CNSwiftRatingBarNode(
                id: ratingNode.id,
                rating: Int(ratingNode.rating),
                maxRating: Int(ratingNode.maxRating),
                onRatingChange: { ratingNode.onRatingChange(KotlinInt(value: Int32($0))) },
                activeColor: convertColor(ratingNode.activeColor),
                modifiers: modifiers
            )

        case let menuNode as CNMenuNode:
            return CNSwiftMenuNode(
                id: menuNode.id,
                title: menuNode.title,
                items: menuNode.items.map { item in
                    CNSwiftMenuItem(title: item.title, icon: item.icon, isDestructive: item.isDestructive, isEnabled: item.enabled, onClick: { item.onClick() })
                },
                modifiers: modifiers
            )

        case let searchNode as CNSearchBarNode:
            return CNSwiftSearchBarNode(
                id: searchNode.id,
                query: searchNode.query,
                onQueryChange: { searchNode.onQueryChange($0) },
                placeholder: searchNode.placeholder,
                onSearch: { searchNode.onSearch($0) },
                modifiers: modifiers
            )

        case let chipNode as CNChipNode:
            return CNSwiftChipNode(
                id: chipNode.id,
                text: chipNode.text,
                isSelected: chipNode.selected,
                onClick: { chipNode.onClick() },
                icon: chipNode.leadingIcon?.icon,
                modifiers: modifiers
            )

        case let snackbarNode as CNSnackbarNode:
            return CNSwiftSnackbarNode(
                id: snackbarNode.id,
                message: snackbarNode.message,
                actionLabel: snackbarNode.actionLabel,
                onAction: snackbarNode.onAction != nil ? { snackbarNode.onAction?() } : nil,
                modifiers: modifiers
            )

        default:
            return CNSwiftSpacerNode(id: node.id, modifiers: modifiers)
        }
    }

    private static func convertColor(_ color: CNColor) -> CNSwiftColor {
        return CNSwiftColor(
            red: Double(color.red) / 255.0,
            green: Double(color.green) / 255.0,
            blue: Double(color.blue) / 255.0,
            alpha: Double(color.alpha),
            name: color.name
        )
    }

    private static func convertTextStyle(_ style: CNTextStyle) -> CNSwiftTextStyle {
        let weight: CNSwiftFontWeight = {
            switch style.fontWeight.name {
            case "Bold": return .bold
            case "SemiBold", "Semibold": return .semiBold
            case "Medium": return .medium
            case "Light": return .light
            case "ExtraBold": return .extraBold
            case "Thin": return .thin
            default: return .regular
            }
        }()

        return CNSwiftTextStyle(
            color: convertColor(style.color),
            fontSize: CGFloat(style.fontSize),
            fontWeight: weight,
            isItalic: style.fontStyle.name == "Italic",
            letterSpacing: CGFloat(style.letterSpacing),
            lineHeight: style.lineHeight > 0 ? CGFloat(style.lineHeight) : nil
        )
    }

    private static func convertHorizontalAlignment(_ align: CNAlignment.Horizontal) -> HorizontalAlignment {
        if align is CNAlignment.HorizontalCenterHorizontally {
            return .center
        } else if align is CNAlignment.HorizontalEnd {
            return .trailing
        }
        return .leading
    }

    private static func convertKeyboardType(_ type: CNKeyboardType) -> CNSwiftKeyboardType {
        switch type.name {
        case "Email": return .email
        case "Password": return .password
        case "Number": return .number
        case "Phone": return .phone
        case "Decimal": return .decimal
        default: return .default
        }
    }

    private static func convertImageSource(_ source: CNImageSource) -> CNSwiftImageNode.ImageType {
        if let net = source as? CNImageSource.NetworkUrl, let url = URL(string: net.url) {
            return .remote(url)
        } else if let sys = source as? CNImageSource.SystemIcon {
            return .sfSymbol(sys.name)
        } else if let asset = source as? CNImageSource.Asset {
            return .asset(asset.name)
        }
        return .sfSymbol("photo")
    }

    private static func convertModifiers(_ elements: [CNModifierElement]) -> [CNSwiftModifierElement] {
        var swiftMods: [CNSwiftModifierElement] = []
        for elem in elements {
            switch elem {
            case let pad as CNPaddingModifier:
                swiftMods.append(.padding(CNSwiftPadding(
                    top: CGFloat(pad.padding.top),
                    leading: CGFloat(pad.padding.start),
                    bottom: CGFloat(pad.padding.bottom),
                    trailing: CGFloat(pad.padding.end)
                )))
            case let bg as CNBackgroundModifier:
                swiftMods.append(.background(color: convertColor(bg.color), shape: convertShape(bg.shape)))
            case let haptic as CNHapticModifier:
                let hType: ComposeNativeSwift.CNHapticType = {
                    switch haptic.type.name {
                    case "Light": return .light
                    case "Medium": return .medium
                    case "Heavy": return .heavy
                    case "Success": return .success
                    case "Warning": return .warning
                    case "Error": return .error
                    default: return .light
                    }
                }()
                swiftMods.append(.haptic(type: hType))
            case let glass as CNLiquidGlassModifier:
                swiftMods.append(.liquidGlass(CNSwiftLiquidGlassProperties(
                    blurRadius: CGFloat(glass.properties.blurRadius),
                    tint: convertColor(glass.properties.tint),
                    borderHighlight: convertColor(glass.properties.borderHighlight),
                    cornerRadius: CGFloat(glass.properties.cornerRadius),
                    specularOpacity: Double(glass.properties.specularOpacity)
                )))
            case is CNFillMaxWidthModifier:
                swiftMods.append(.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false))
            case is CNFillMaxSizeModifier:
                swiftMods.append(.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: true))
            case let h as CNHeightModifier:
                swiftMods.append(.frame(width: nil, height: CGFloat(h.height), fillMaxWidth: false, fillMaxHeight: false))
            case let s as CNSizeModifier:
                swiftMods.append(.frame(width: CGFloat(s.width), height: CGFloat(s.height), fillMaxWidth: false, fillMaxHeight: false))
            case let click as CNClickableModifier:
                swiftMods.append(.clickable(action: { click.onClick() }))
            case let mat as CNMaterialModifier:
                let mType: CNSwiftMaterialType = {
                    switch mat.material.name {
                    case "UltraThin": return .ultraThin
                    case "Thin": return .thin
                    case "Regular": return .regular
                    case "Thick": return .thick
                    case "UltraThick": return .ultraThick
                    default: return .ultraThin
                    }
                }()
                swiftMods.append(.material(type: mType, shape: convertShape(mat.shape)))
            default:
                break
            }
        }
        return swiftMods
    }

    private static func convertShape(_ shape: CNShape) -> CNSwiftShape {
        if shape is CNShape.Circle {
            return .circle
        } else if shape is CNShape.Capsule {
            return .capsule
        } else if let rounded = shape as? CNShape.RoundedCorner {
            return .roundedCorner(radius: CGFloat(rounded.topStart))
        }
        return .rectangle
    }
}
