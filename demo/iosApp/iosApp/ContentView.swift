import SwiftUI
import ComposeNativeSwift

/**
 * iOS Demo Main UI showing multiple shared Compose screens rendered natively via SwiftUI!
 *
 * Developers only write 1 line per shared Kotlin screen:
 * `ComposeNativeView(screen: MyKotlinScreen())`
 */
struct ContentView: View {
    @State private var selectedTab = 0

    var body: some View {
        TabView(selection: $selectedTab) {
            CounterDemoTab()
                .tabItem {
                    Label("Counter", systemImage: "plus.forwardslash.minus")
                }
                .tag(0)

            FormDemoTab()
                .tabItem {
                    Label("Form", systemImage: "pencil.and.list.clipboard")
                }
                .tag(1)

            FeedDemoTab()
                .tabItem {
                    Label("Feed", systemImage: "rectangle.stack")
                }
                .tag(2)

            ProfileDemoTab()
                .tabItem {
                    Label("Profile", systemImage: "person.crop.circle")
                }
                .tag(3)

            SettingsDemoTab()
                .tabItem {
                    Label("Settings", systemImage: "gearshape")
                }
                .tag(4)
        }
    }
}

// MARK: - Demo Tab Views

struct CounterDemoTab: View {
    @StateObject private var state = CounterScreenState()

    var body: some View {
        ComposeNativeView(node: state.buildNode())
    }
}

class CounterScreenState: ObservableObject {
    @Published var count: Int = 0
    @Published var step: Float = 1.0

    func buildNode() -> CNRenderableNode {
        CNSwiftScaffoldNode(
            topBarTitle: "ComposeNative Counter",
            content: CNSwiftColumnNode(
                spacing: 20,
                horizontalAlignment: .center,
                children: [
                    CNSwiftCardNode(
                        elevation: 3,
                        content: CNSwiftColumnNode(
                            spacing: 12,
                            horizontalAlignment: .center,
                            children: [
                                CNSwiftTextNode(
                                    text: "Native SwiftUI from Compose!",
                                    style: CNSwiftTextStyle(
                                        color: .primary,
                                        fontSize: 20,
                                        fontWeight: .bold
                                    )
                                ),
                                CNSwiftTextNode(
                                    text: "Current Count",
                                    style: CNSwiftTextStyle(
                                        color: .gray,
                                        fontSize: 14
                                    )
                                ),
                                CNSwiftTextNode(
                                    text: "\(count)",
                                    style: CNSwiftTextStyle(
                                        color: count > 0 ? .success : (count < 0 ? .error : .onSurface),
                                        fontSize: 48,
                                        fontWeight: .extraBold
                                    )
                                )
                            ],
                            modifiers: [.padding(CNSwiftPadding(all: 20))]
                        ),
                        modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                    ),
                    CNSwiftRowNode(
                        spacing: 12,
                        children: [
                            CNSwiftButtonNode(
                                onClick: { [weak self] in
                                    guard let self = self else { return }
                                    self.count -= Int(self.step)
                                },
                                content: CNSwiftTextNode(
                                    text: "-\(Int(step))",
                                    style: CNSwiftTextStyle(color: .surface, fontWeight: .bold)
                                ),
                                modifiers: [
                                    .frame(width: 90, height: 48, fillMaxWidth: false, fillMaxHeight: false),
                                    .background(color: .error, shape: .roundedCorner(radius: 10))
                                ]
                            ),
                            CNSwiftButtonNode(
                                onClick: { [weak self] in
                                    self?.count = 0
                                },
                                content: CNSwiftTextNode(
                                    text: "Reset",
                                    style: CNSwiftTextStyle(color: .surface, fontWeight: .bold)
                                ),
                                modifiers: [
                                    .frame(width: 90, height: 48, fillMaxWidth: false, fillMaxHeight: false),
                                    .background(color: .gray, shape: .roundedCorner(radius: 10))
                                ]
                            ),
                            CNSwiftButtonNode(
                                onClick: { [weak self] in
                                    guard let self = self else { return }
                                    self.count += Int(self.step)
                                },
                                content: CNSwiftTextNode(
                                    text: "+\(Int(step))",
                                    style: CNSwiftTextStyle(color: .surface, fontWeight: .bold)
                                ),
                                modifiers: [
                                    .frame(width: 90, height: 48, fillMaxWidth: false, fillMaxHeight: false),
                                    .background(color: .success, shape: .roundedCorner(radius: 10))
                                ]
                            )
                        ]
                    ),
                    CNSwiftCardNode(
                        elevation: 1,
                        content: CNSwiftColumnNode(
                            spacing: 8,
                            children: [
                                CNSwiftTextNode(
                                    text: "Step Size: \(Int(step))",
                                    style: CNSwiftTextStyle(fontSize: 14, fontWeight: .semiBold)
                                ),
                                CNSwiftSliderNode(
                                    value: step,
                                    onValueChange: { [weak self] newStep in
                                        self?.step = newStep
                                    },
                                    min: 1,
                                    max: 10,
                                    step: 1
                                )
                            ],
                            modifiers: [.padding(CNSwiftPadding(all: 16))]
                        ),
                        modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                    )
                ],
                modifiers: [
                    .padding(CNSwiftPadding(all: 20)),
                    .frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: true)
                ]
            )
        )
    }
}

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
    @Published var experienceYears: Float = 4.0
    @Published var isSubmitted = false

    func buildNode() -> CNRenderableNode {
        CNSwiftScaffoldNode(
            topBarTitle: "User Registration",
            content: CNSwiftLazyListNode(
                spacing: 16,
                contentPadding: CNSwiftPadding(horizontal: 16, vertical: 16),
                children: [
                    CNSwiftTextNode(
                        text: "SwiftUI Native Form",
                        style: CNSwiftTextStyle(fontSize: 22, fontWeight: .bold)
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
                        placeholder: "Password",
                        isSecure: true
                    ),
                    CNSwiftCardNode(
                        elevation: 1,
                        content: CNSwiftRowNode(
                            children: [
                                CNSwiftTextNode(text: "Subscribe to Updates", style: CNSwiftTextStyle(fontWeight: .medium)),
                                CNSwiftSpacerNode(),
                                CNSwiftSwitchNode(
                                    isChecked: isSubscribed,
                                    onCheckedChange: { [weak self] in self?.isSubscribed = $0 }
                                )
                            ],
                            modifiers: [.padding(CNSwiftPadding(all: 14))]
                        ),
                        modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                    ),
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
                ]
            )
        )
    }
}

struct FeedDemoTab: View {
    var body: some View {
        ComposeNativeView(
            node: CNSwiftScaffoldNode(
                topBarTitle: "Explore Feed",
                content: CNSwiftLazyListNode(
                    spacing: 14,
                    contentPadding: CNSwiftPadding(horizontal: 16, vertical: 12),
                    children: [
                        createPostCard(
                            author: "Alex Rivers",
                            tag: "KMP",
                            content: "ComposeNativeSwift is translating Compose UI directly to SwiftUI views on iOS!"
                        ),
                        createPostCard(
                            author: "Sophia Chen",
                            tag: "SwiftUI",
                            content: "Native text selection, accessibility, VoiceOver, and 120Hz ProMotion work out of the box."
                        ),
                        createPostCard(
                            author: "Marcus Vance",
                            tag: "Zero Config",
                            content: "Literally 1 line in SwiftUI: ComposeNativeView(screen: MyScreen())"
                        )
                    ]
                )
            )
        )
    }

    private func createPostCard(author: String, tag: String, content: String) -> CNRenderableNode {
        CNSwiftCardNode(
            elevation: 2,
            content: CNSwiftColumnNode(
                spacing: 10,
                children: [
                    CNSwiftRowNode(
                        children: [
                            CNSwiftImageNode(source: .sfSymbol("person.circle.fill"), contentDescription: nil, tint: .primary, modifiers: [.frame(width: 32, height: 32, fillMaxWidth: false, fillMaxHeight: false)]),
                            CNSwiftTextNode(text: "  \(author)", style: CNSwiftTextStyle(fontWeight: .bold)),
                            CNSwiftSpacerNode(),
                            CNSwiftBadgeNode(text: tag, backgroundColor: .accent, contentColor: .surface)
                        ]
                    ),
                    CNSwiftTextNode(text: content, style: CNSwiftTextStyle(fontSize: 15)),
                    CNSwiftDividerNode(),
                    CNSwiftRowNode(
                        children: [
                            CNSwiftTextNode(text: "❤️ Like", style: CNSwiftTextStyle(color: .error, fontWeight: .medium)),
                            CNSwiftSpacerNode(),
                            CNSwiftTextNode(text: "Share", style: CNSwiftTextStyle(color: .primary, fontWeight: .medium))
                        ]
                    )
                ],
                modifiers: [.padding(CNSwiftPadding(all: 16))]
            ),
            modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
        )
    }
}

struct ProfileDemoTab: View {
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
                                                onClick: {},
                                                content: CNSwiftTextNode(text: "Follow", style: CNSwiftTextStyle(color: .surface, fontWeight: .bold)),
                                                modifiers: [
                                                    .frame(width: 120, height: 40, fillMaxWidth: false, fillMaxHeight: false),
                                                    .background(color: .primary, shape: .roundedCorner(radius: 8))
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

struct SettingsDemoTab: View {
    @State private var darkTheme = false
    @State private var notifications = true

    var body: some View {
        ComposeNativeView(
            node: CNSwiftScaffoldNode(
                topBarTitle: "Settings",
                content: CNSwiftLazyListNode(
                    spacing: 12,
                    contentPadding: CNSwiftPadding(all: 16),
                    children: [
                        CNSwiftCardNode(
                            elevation: 1,
                            content: CNSwiftColumnNode(
                                spacing: 10,
                                children: [
                                    CNSwiftRowNode(
                                        children: [
                                            CNSwiftTextNode(text: "Dark Theme"),
                                            CNSwiftSpacerNode(),
                                            CNSwiftSwitchNode(isChecked: darkTheme, onCheckedChange: { darkTheme = $0 })
                                        ]
                                    ),
                                    CNSwiftDividerNode(),
                                    CNSwiftRowNode(
                                        children: [
                                            CNSwiftTextNode(text: "Notifications"),
                                            CNSwiftSpacerNode(),
                                            CNSwiftSwitchNode(isChecked: notifications, onCheckedChange: { notifications = $0 })
                                        ]
                                    )
                                ],
                                modifiers: [.padding(CNSwiftPadding(all: 14))]
                            ),
                            modifiers: [.frame(width: nil, height: nil, fillMaxWidth: true, fillMaxHeight: false)]
                        ),
                        CNSwiftCardNode(
                            elevation: 1,
                            content: CNSwiftRowNode(
                                children: [
                                    CNSwiftTextNode(text: "ComposeNativeSwift", style: CNSwiftTextStyle(color: .gray)),
                                    CNSwiftSpacerNode(),
                                    CNSwiftTextNode(text: "v1.0.0", style: CNSwiftTextStyle(fontWeight: .semiBold))
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
}
