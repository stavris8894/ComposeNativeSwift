import SwiftUI
import ComposeNativeSwift

/**
 * Enhanced iOS Demo showcasing complete UI Component suite & Material 3 Dark Theme!
 */
struct ContentView: View {
    @StateObject private var themeState = CNSwiftThemeState.shared
    @Environment(\.colorScheme) private var systemColorScheme
    @State private var selectedTab = 0

    var body: some View {
        TabView(selection: $selectedTab) {
            ComponentsCatalogTab()
                .tabItem {
                    Label("Components", systemImage: "square.grid.2x2")
                }
                .tag(0)

            FormDemoTab()
                .tabItem {
                    Label("Form", systemImage: "pencil.and.list.clipboard")
                }
                .tag(1)

            GridFeedDemoTab()
                .tabItem {
                    Label("Grid & Feed", systemImage: "rectangle.stack")
                }
                .tag(2)

            ProfileDemoTab()
                .tabItem {
                    Label("Profile", systemImage: "person.crop.circle")
                }
                .tag(3)

            ThemeSettingsTab()
                .tabItem {
                    Label("Theme", systemImage: "paintpalette")
                }
                .tag(4)
        }
        .preferredColorScheme(themeState.isDarkMode ? .dark : .light)
        .onAppear {
            themeState.isDarkMode = (systemColorScheme == .dark)
        }
    }
}

// MARK: - Tab 1: Full Components Catalog

struct ComponentsCatalogTab: View {
    @StateObject private var state = ComponentsCatalogState()

    var body: some View {
        ComposeNativeView(node: state.buildNode())
    }
}

class ComponentsCatalogState: ObservableObject {
    @Published var selectedSegment = 0
    @Published var selectedChipIndex = 0
    @Published var selectedRadio = 0
    @Published var switchState = true
    @Published var sliderVal: Float = 65
    @Published var rangeMin: Float = 0.2
    @Published var rangeMax: Float = 0.8
    @Published var isAccordionExpanded = true

    func buildNode() -> CNRenderableNode {
        CNSwiftScaffoldNode(
            topBarTitle: "UI Components Hub",
            content: CNSwiftLazyListNode(
                spacing: 16,
                contentPadding: CNSwiftPadding(all: 16),
                children: [
                    // Section 1: Segmented Controls
                    createSectionHeader("Segmented Control"),
                    CNSwiftSegmentedButtonNode(
                        items: ["All", "Favorites", "Recent", "Archived"],
                        selectedIndex: selectedSegment,
                        onSelectIndex: { [weak self] in self?.selectedSegment = $0 }
                    ),

                    // Section 2: Chips
                    createSectionHeader("Filter & Action Chips"),
                    CNSwiftRowNode(
                        spacing: 8,
                        children: [
                            CNSwiftChipNode(
                                text: "Kotlin",
                                isSelected: selectedChipIndex == 0,
                                onClick: { [weak self] in self?.selectedChipIndex = 0 },
                                icon: "chevron.left.forwardslash.chevron.right"
                            ),
                            CNSwiftChipNode(
                                text: "SwiftUI",
                                isSelected: selectedChipIndex == 1,
                                onClick: { [weak self] in self?.selectedChipIndex = 1 },
                                icon: "swift"
                            ),
                            CNSwiftChipNode(
                                text: "Compose",
                                isSelected: selectedChipIndex == 2,
                                onClick: { [weak self] in self?.selectedChipIndex = 2 },
                                icon: "sparkles"
                            )
                        ]
                    ),

                    // Section 3: Radio Buttons
                    createSectionHeader("Radio Button Selection"),
                    CNSwiftCardNode(
                        elevation: 1,
                        content: CNSwiftColumnNode(
                            spacing: 12,
                            children: [
                                CNSwiftRadioButtonNode(
                                    isSelected: selectedRadio == 0,
                                    onClick: { [weak self] in self?.selectedRadio = 0 },
                                    label: "Standard Native Resolution"
                                ),
                                CNSwiftDividerNode(),
                                CNSwiftRadioButtonNode(
                                    isSelected: selectedRadio == 1,
                                    onClick: { [weak self] in self?.selectedRadio = 1 },
                                    label: "High Performance 120Hz Mode"
                                ),
                                CNSwiftDividerNode(),
                                CNSwiftRadioButtonNode(
                                    isSelected: selectedRadio == 2,
                                    onClick: { [weak self] in self?.selectedRadio = 2 },
                                    label: "Battery Optimized Mode"
                                )
                            ],
                            modifiers: [.padding(CNSwiftPadding(all: 14))]
                        ),
                        modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                    ),

                    // Section 4: Sliders & RangeSlider
                    createSectionHeader("Sliders & Range Sliders"),
                    CNSwiftCardNode(
                        elevation: 1,
                        content: CNSwiftColumnNode(
                            spacing: 14,
                            children: [
                                CNSwiftRowNode(
                                    children: [
                                        CNSwiftTextNode(text: "Single Slider: \(Int(sliderVal))%"),
                                        CNSwiftSpacerNode(),
                                        CNSwiftBadgeNode(text: "Level \(Int(sliderVal / 20) + 1)", backgroundColor: .primary, contentColor: .surface)
                                    ]
                                ),
                                CNSwiftSliderNode(
                                    value: sliderVal,
                                    onValueChange: { [weak self] in self?.sliderVal = $0 },
                                    min: 0,
                                    max: 100
                                ),
                                CNSwiftDividerNode(),
                                CNSwiftTextNode(text: "Dual-Thumb Range Slider", style: CNSwiftTextStyle(fontSize: 14, fontWeight: .medium)),
                                CNSwiftRangeSliderNode(
                                    startValue: rangeMin,
                                    endValue: rangeMax,
                                    onValuesChange: { [weak self] min, max in
                                        self?.rangeMin = min
                                        self?.rangeMax = max
                                    }
                                )
                            ],
                            modifiers: [.padding(CNSwiftPadding(all: 14))]
                        ),
                        modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                    ),

                    // Section 5: List Items
                    createSectionHeader("Material 3 List Items"),
                    CNSwiftListItemNode(
                        headline: CNSwiftTextNode(text: "Native VoiceOver & Accessibility", style: CNSwiftTextStyle(fontWeight: .semiBold)),
                        supporting: CNSwiftTextNode(text: "Zero configuration screen reader support", style: CNSwiftTextStyle(color: .gray, fontSize: 13)),
                        leading: CNSwiftImageNode(source: .sfSymbol("accessibility"), tint: .primary, modifiers: [.frame(width: 28, height: 28, fillMaxWidth: false, fillMaxHeight: false)]),
                        trailing: CNSwiftBadgeNode(text: "Native", backgroundColor: .success, contentColor: .surface)
                    ),
                    CNSwiftListItemNode(
                        headline: CNSwiftTextNode(text: "ProMotion 120 FPS Rendering", style: CNSwiftTextStyle(fontWeight: .semiBold)),
                        supporting: CNSwiftTextNode(text: "Smooth declarative animation pipeline", style: CNSwiftTextStyle(color: .gray, fontSize: 13)),
                        leading: CNSwiftImageNode(source: .sfSymbol("bolt.fill"), tint: .accent, modifiers: [.frame(width: 28, height: 28, fillMaxWidth: false, fillMaxHeight: false)]),
                        trailing: CNSwiftBadgeNode(text: "Fast", backgroundColor: .secondary, contentColor: .surface)
                    ),

                    // Section 6: Expandable Accordion
                    createSectionHeader("Expandable Accordion"),
                    CNSwiftAccordionNode(
                        title: "Why ComposeNativeSwift over Skiko?",
                        isExpanded: isAccordionExpanded,
                        onToggle: { [weak self] in self?.isAccordionExpanded = $0 },
                        content: CNSwiftColumnNode(
                            spacing: 8,
                            children: [
                                CNSwiftTextNode(text: "1. 100% Genuine SwiftUI native widgets."),
                                CNSwiftTextNode(text: "2. Native iOS keyboards, autocorrect & password autofill."),
                                CNSwiftTextNode(text: "3. Seamless Dark Mode and dynamic type integration."),
                                CNSwiftTextNode(text: "4. No canvas rendering overhead or battery drain.")
                            ]
                        )
                    ),

                    // Section 7: Progress & Badges
                    createSectionHeader("Feedback & Progress"),
                    CNSwiftCardNode(
                        elevation: 1,
                        content: CNSwiftRowNode(
                            spacing: 16,
                            children: [
                                CNSwiftProgressNode(isCircular: true, progress: 0.72, color: .primary),
                                CNSwiftColumnNode(
                                    spacing: 6,
                                    children: [
                                        CNSwiftTextNode(text: "Circular & Linear Progress"),
                                        CNSwiftProgressNode(isCircular: false, progress: 0.72, color: .success)
                                    ]
                                )
                            ],
                            modifiers: [.padding(CNSwiftPadding(all: 14))]
                        ),
                        modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                    )
                ]
            )
        )
    }

    private func createSectionHeader(_ title: String) -> CNRenderableNode {
        CNSwiftTextNode(
            text: title,
            style: CNSwiftTextStyle(
                color: .primary,
                fontSize: 16,
                fontWeight: .bold
            ),
            modifiers: [.padding(CNSwiftPadding(top: 8))]
        )
    }
}

// MARK: - Tab 2: Form & Inputs

struct FormDemoTab: View {
    @StateObject private var state = FormScreenState()

    var body: some View {
        ComposeNativeView(node: state.buildNode())
    }
}

class FormScreenState: ObservableObject {
    @Published var name = ""
    @Published var email = ""
    @Published var password = ""
    @Published var isSubscribed = true
    @Published var isSubmitted = false

    func buildNode() -> CNRenderableNode {
        var items: [CNRenderableNode] = [
            CNSwiftTextNode(
                text: "Native Form Controls",
                style: CNSwiftTextStyle(fontSize: 22, fontWeight: .bold)
            ),
            CNSwiftTextNode(
                text: "Form fields render natively with iOS autocorrect, secure text entry, and validation.",
                style: CNSwiftTextStyle(color: .gray, fontSize: 14)
            ),
            CNSwiftTextFieldNode(
                value: name,
                onValueChange: { [weak self] in self?.name = $0 },
                placeholder: "Full Name"
            ),
            CNSwiftTextFieldNode(
                value: email,
                onValueChange: { [weak self] in self?.email = $0 },
                placeholder: "Email Address",
                keyboardType: .email
            ),
            CNSwiftTextFieldNode(
                value: password,
                onValueChange: { [weak self] in self?.password = $0 },
                placeholder: "Password (Secure)",
                isSecure: true
            ),
            CNSwiftCardNode(
                elevation: 1,
                content: CNSwiftRowNode(
                    children: [
                        CNSwiftTextNode(text: "Subscribe to Newsletter", style: CNSwiftTextStyle(fontWeight: .medium)),
                        CNSwiftSpacerNode(),
                        CNSwiftSwitchNode(
                            isChecked: isSubscribed,
                            onCheckedChange: { [weak self] in self?.isSubscribed = $0 }
                        )
                    ],
                    modifiers: [.padding(CNSwiftPadding(all: 14))]
                ),
                modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
            )
        ]

        if isSubmitted {
            items.append(
                CNSwiftCardNode(
                    elevation: 1,
                    backgroundColor: .success,
                    content: CNSwiftTextNode(
                        text: "Registration Successful for \(name)!",
                        style: CNSwiftTextStyle(color: .surface, fontWeight: .bold),
                        modifiers: [.padding(CNSwiftPadding(all: 12))]
                    ),
                    modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                )
            )
        }

        items.append(
            CNSwiftButtonNode(
                onClick: { [weak self] in
                    self?.isSubmitted = true
                },
                content: CNSwiftTextNode(
                    text: "Submit Form",
                    style: CNSwiftTextStyle(color: .surface, fontWeight: .bold)
                ),
                modifiers: [
                    .frame(width: nil, height: 50, fillMaxWidth: true, fillMaxHeight: false),
                    .background(color: .primary, shape: .roundedCorner(radius: 12))
                ]
            )
        )

        return CNSwiftScaffoldNode(
            topBarTitle: "User Registration",
            content: CNSwiftLazyListNode(
                spacing: 16,
                contentPadding: CNSwiftPadding(all: 16),
                children: items
            )
        )
    }
}

// MARK: - Tab 3: Grid & Feed

struct GridFeedDemoTab: View {
    var body: some View {
        ComposeNativeView(
            node: CNSwiftScaffoldNode(
                topBarTitle: "Grid & Feed Showcase",
                content: CNSwiftLazyGridNode(
                    columnsCount: 2,
                    spacing: 12,
                    contentPadding: CNSwiftPadding(all: 16),
                    children: [
                        createGridCard(title: "Architecture", icon: "square.3.layers.3d.down.right", tag: "Core", color: .primary),
                        createGridCard(title: "Compose DSL", icon: "curlybraces", tag: "Kotlin", color: .secondary),
                        createGridCard(title: "SwiftUI Bridge", icon: "swift", tag: "Native", color: .accent),
                        createGridCard(title: "Dark Theme", icon: "moon.stars.fill", tag: "M3", color: .error),
                        createGridCard(title: "120Hz Display", icon: "speedometer", tag: "ProMotion", color: .success),
                        createGridCard(title: "Zero Config", icon: "checkmark.seal.fill", tag: "SPM", color: .tertiary)
                    ]
                )
            )
        )
    }

    private func createGridCard(title: String, icon: String, tag: String, color: CNSwiftColor) -> CNRenderableNode {
        CNSwiftCardNode(
            elevation: 2,
            content: CNSwiftColumnNode(
                spacing: 10,
                children: [
                    CNSwiftRowNode(
                        children: [
                            CNSwiftImageNode(source: .sfSymbol(icon), tint: color, modifiers: [.frame(width: 28, height: 28, fillMaxWidth: false, fillMaxHeight: false)]),
                            CNSwiftSpacerNode(),
                            CNSwiftBadgeNode(text: tag, backgroundColor: color, contentColor: .surface)
                        ]
                    ),
                    CNSwiftTextNode(text: title, style: CNSwiftTextStyle(fontSize: 16, fontWeight: .bold)),
                    CNSwiftTextNode(text: "100% Native SwiftUI rendering", style: CNSwiftTextStyle(color: .gray, fontSize: 12))
                ],
                modifiers: [.padding(CNSwiftPadding(all: 14))]
            ),
            modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
        )
    }
}

// MARK: - Tab 4: Profile

struct ProfileDemoTab: View {
    @State private var isFollowing = false

    var body: some View {
        ComposeNativeView(
            node: CNSwiftScaffoldNode(
                topBarTitle: "Profile",
                content: CNSwiftColumnNode(
                    spacing: 16,
                    horizontalAlignment: .center,
                    children: [
                        CNSwiftCardNode(
                            elevation: 3,
                            content: CNSwiftColumnNode(
                                spacing: 12,
                                horizontalAlignment: .center,
                                children: [
                                    CNSwiftImageNode(source: .sfSymbol("person.crop.circle.badge.checkmark"), tint: .primary, modifiers: [.frame(width: 80, height: 80, fillMaxWidth: false, fillMaxHeight: false)]),
                                    CNSwiftTextNode(text: "Elena Rostova", style: CNSwiftTextStyle(fontSize: 22, fontWeight: .bold)),
                                    CNSwiftTextNode(text: "Staff Mobile Architect", style: CNSwiftTextStyle(color: .gray, fontSize: 14)),
                                    CNSwiftTextNode(
                                        text: "Building cross-platform mobile apps with Kotlin Multiplatform and native SwiftUI.",
                                        style: CNSwiftTextStyle(alignment: .center)
                                    ),
                                    CNSwiftRowNode(
                                        children: [
                                            CNSwiftButtonNode(
                                                onClick: { isFollowing.toggle() },
                                                content: CNSwiftTextNode(text: isFollowing ? "Following" : "Follow", style: CNSwiftTextStyle(color: .surface, fontWeight: .bold)),
                                                modifiers: [
                                                    .frame(width: 120, height: 40, fillMaxWidth: false, fillMaxHeight: false),
                                                    .background(color: isFollowing ? .gray : .primary, shape: .roundedCorner(radius: 8))
                                                ]
                                            ),
                                            CNSwiftButtonNode(
                                                onClick: {},
                                                content: CNSwiftTextNode(text: "Message", style: CNSwiftTextStyle(fontWeight: .bold)),
                                                modifiers: [
                                                    .frame(width: 120, height: 40, fillMaxWidth: false, fillMaxHeight: false),
                                                    .background(color: .lightGray, shape: .roundedCorner(radius: 8))
                                                ]
                                            )
                                        ],
                                        modifiers: [.padding(CNSwiftPadding(top: 8))]
                                    )
                                ],
                                modifiers: [.padding(CNSwiftPadding(all: 20))]
                            ),
                            modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                        )
                    ],
                    modifiers: [.padding(CNSwiftPadding(all: 16))]
                )
            )
        )
    }
}

// MARK: - Tab 5: Theme & Dark Mode Settings

struct ThemeSettingsTab: View {
    @ObservedObject private var themeState = CNSwiftThemeState.shared

    var body: some View {
        ComposeNativeView(
            node: CNSwiftScaffoldNode(
                topBarTitle: "Theme & Customization",
                content: CNSwiftLazyListNode(
                    spacing: 14,
                    contentPadding: CNSwiftPadding(all: 16),
                    children: [
                        // Dark Theme Toggle Card
                        CNSwiftCardNode(
                            elevation: 2,
                            content: CNSwiftColumnNode(
                                spacing: 12,
                                children: [
                                    CNSwiftRowNode(
                                        children: [
                                            CNSwiftImageNode(
                                                source: .sfSymbol(themeState.isDarkMode ? "moon.stars.fill" : "sun.max.fill"),
                                                tint: themeState.isDarkMode ? .accent : .primary,
                                                modifiers: [.frame(width: 28, height: 28, fillMaxWidth: false, fillMaxHeight: false)]
                                            ),
                                            CNSwiftColumnNode(
                                                spacing: 2,
                                                children: [
                                                    CNSwiftTextNode(
                                                        text: "Dark Theme Mode",
                                                        style: CNSwiftTextStyle(fontSize: 16, fontWeight: .bold)
                                                    ),
                                                    CNSwiftTextNode(
                                                        text: themeState.isDarkMode ? "Material 3 Dark Palette Active" : "Material 3 Light Palette Active",
                                                        style: CNSwiftTextStyle(color: .gray, fontSize: 12)
                                                    )
                                                ],
                                                modifiers: [.padding(CNSwiftPadding(leading: 8))]
                                            ),
                                            CNSwiftSpacerNode(),
                                            CNSwiftSwitchNode(
                                                isChecked: themeState.isDarkMode,
                                                onCheckedChange: { [weak themeState] in
                                                    themeState?.isDarkMode = $0
                                                },
                                                tint: .primary
                                            )
                                        ]
                                    )
                                ],
                                modifiers: [.padding(CNSwiftPadding(all: 16))]
                            ),
                            modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                        ),

                        // Color Palette Tokens
                        CNSwiftTextNode(
                            text: "Material 3 Color Scheme Tokens",
                            style: CNSwiftTextStyle(color: .primary, fontSize: 16, fontWeight: .bold),
                            modifiers: [.padding(CNSwiftPadding(top: 8))]
                        ),

                        CNSwiftCardNode(
                            elevation: 1,
                            content: CNSwiftColumnNode(
                                spacing: 10,
                                children: [
                                    createPaletteRow(name: "Primary", color: themeState.isDarkMode ? CNSwiftColor(hex: "#0A84FF") : CNSwiftColor(hex: "#007AFF")),
                                    CNSwiftDividerNode(),
                                    createPaletteRow(name: "Secondary", color: themeState.isDarkMode ? CNSwiftColor(hex: "#5E5CE6") : CNSwiftColor(hex: "#5856D6")),
                                    CNSwiftDividerNode(),
                                    createPaletteRow(name: "Tertiary", color: themeState.isDarkMode ? CNSwiftColor(hex: "#64D2FF") : CNSwiftColor(hex: "#009688")),
                                    CNSwiftDividerNode(),
                                    createPaletteRow(name: "Success", color: themeState.isDarkMode ? CNSwiftColor(hex: "#32D74B") : CNSwiftColor(hex: "#34C759")),
                                    CNSwiftDividerNode(),
                                    createPaletteRow(name: "Error", color: themeState.isDarkMode ? CNSwiftColor(hex: "#FF453A") : CNSwiftColor(hex: "#FF3B30"))
                                ],
                                modifiers: [.padding(CNSwiftPadding(all: 14))]
                            ),
                            modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                        ),

                        // Library Metadata
                        CNSwiftCardNode(
                            elevation: 1,
                            content: CNSwiftColumnNode(
                                spacing: 6,
                                children: [
                                    CNSwiftRowNode(
                                        children: [
                                            CNSwiftTextNode(text: "Engine", style: CNSwiftTextStyle(color: .gray)),
                                            CNSwiftSpacerNode(),
                                            CNSwiftTextNode(text: "ComposeNativeSwift v1.2.0", style: CNSwiftTextStyle(fontWeight: .bold))
                                        ]
                                    ),
                                    CNSwiftRowNode(
                                        children: [
                                            CNSwiftTextNode(text: "Rendering Target", style: CNSwiftTextStyle(color: .gray)),
                                            CNSwiftSpacerNode(),
                                            CNSwiftTextNode(text: "100% Genuine SwiftUI", style: CNSwiftTextStyle(color: .primary, fontWeight: .bold))
                                        ]
                                    )
                                ],
                                modifiers: [.padding(CNSwiftPadding(all: 14))]
                            ),
                            modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                        )
                    ]
                )
            )
        )
    }

    private func createPaletteRow(name: String, color: CNSwiftColor) -> CNRenderableNode {
        CNSwiftRowNode(
            children: [
                CNSwiftCardNode(
                    elevation: 0,
                    backgroundColor: color,
                    content: CNSwiftSpacerNode(),
                    modifiers: [
                        .frame(width: 24, height: 24, fillMaxWidth: false, fillMaxHeight: false),
                        .cornerRadius(6)
                    ]
                ),
                CNSwiftTextNode(text: "  \(name)", style: CNSwiftTextStyle(fontWeight: .medium)),
                CNSwiftSpacerNode(),
                CNSwiftTextNode(text: color.name ?? "Hex", style: CNSwiftTextStyle(color: .gray, fontSize: 13))
            ]
        )
    }
}
