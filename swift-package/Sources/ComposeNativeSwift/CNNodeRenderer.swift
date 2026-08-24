import SwiftUI

public struct CNNodeRenderer: View {
    @ObservedObject private var themeState = CNSwiftThemeState.shared
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

        case let datePickerNode as CNSwiftDatePickerNode:
            renderDatePicker(datePickerNode)

        case let stepperNode as CNSwiftStepperNode:
            renderStepper(stepperNode)

        case let ratingNode as CNSwiftRatingBarNode:
            renderRatingBar(ratingNode)

        case let menuNode as CNSwiftMenuNode:
            renderMenu(menuNode)

        case let pagerNode as CNSwiftPagerNode:
            renderPager(pagerNode)

        case let searchBarNode as CNSwiftSearchBarNode:
            renderSearchBar(searchBarNode)

        case let snackbarNode as CNSwiftSnackbarNode:
            renderSnackbar(snackbarNode)

        case let switchNode as CNSwiftSwitchNode:
            renderSwitch(switchNode)

        case let sliderNode as CNSwiftSliderNode:
            renderSlider(sliderNode)

        case let rangeSliderNode as CNSwiftRangeSliderNode:
            renderRangeSlider(rangeSliderNode)

        case let radioNode as CNSwiftRadioButtonNode:
            renderRadioButton(radioNode)

        case let chipNode as CNSwiftChipNode:
            renderChip(chipNode)

        case let segmentedNode as CNSwiftSegmentedButtonNode:
            renderSegmentedButton(segmentedNode)

        case let listItemNode as CNSwiftListItemNode:
            renderListItem(listItemNode)

        case let accordionNode as CNSwiftAccordionNode:
            renderAccordion(accordionNode)

        case let bannerNode as CNSwiftBannerNode:
            renderBanner(bannerNode)

        case let tabRowNode as CNSwiftTabRowNode:
            renderTabRow(tabRowNode)

        case let lazyListNode as CNSwiftLazyListNode:
            renderLazyList(lazyListNode)

        case let lazyGridNode as CNSwiftLazyGridNode:
            renderLazyGrid(lazyGridNode)

        case let imageNode as CNSwiftImageNode:
            renderImage(imageNode)

        case let cardNode as CNSwiftCardNode:
            renderCard(cardNode)

        case let scaffoldNode as CNSwiftScaffoldNode:
            renderScaffold(scaffoldNode)

        case let navHostNode as CNSwiftNavHostNode:
            renderNavHost(navHostNode)

        case let liquidGlassNode as CNSwiftLiquidGlassNode:
            renderLiquidGlass(liquidGlassNode)

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

    // MARK: - Navigation & Liquid Glass Renderers

    @ViewBuilder
    private func renderNavHost(_ node: CNSwiftNavHostNode) -> some View {
        let bgColor = themeState.isDarkMode ? Color.black : Color(red: 0.95, green: 0.95, blue: 0.97)
        ZStack(alignment: .top) {
            bgColor.ignoresSafeArea()

            VStack(spacing: 0) {
                if node.navBarStyle != "Hidden" {
                    CNLiquidGlassNavBar(
                        title: node.currentTitle,
                        showBackButton: node.showBackButton,
                        onBack: node.onPopBack
                    )
                }

                CNNodeRenderer(node: node.content)
            }
        }
    }

    @ViewBuilder
    private func renderLiquidGlass(_ node: CNSwiftLiquidGlassNode) -> some View {
        CNLiquidGlassContainer(
            properties: node.properties,
            content: AnyView(CNNodeRenderer(node: node.content))
        )
    }

    // MARK: - Core Renderers

    @ViewBuilder
    private func renderText(_ node: CNSwiftTextNode) -> some View {
        let defaultColor = themeState.isDarkMode ? Color.white : Color(red: 0.11, green: 0.11, blue: 0.12)
        let resolvedColor = node.style.color.name == "onSurface" ? defaultColor : node.style.color.swiftUIColor

        if node.style.isItalic {
            Text(node.text)
                .font(.system(size: node.style.fontSize, weight: node.style.fontWeight.swiftUIFontWeight))
                .foregroundColor(resolvedColor)
                .italic()
                .multilineTextAlignment(node.style.alignment)
                .lineLimit(node.maxLines)
        } else {
            Text(node.text)
                .font(.system(size: node.style.fontSize, weight: node.style.fontWeight.swiftUIFontWeight))
                .foregroundColor(resolvedColor)
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
    private func renderDatePicker(_ node: CNSwiftDatePickerNode) -> some View {
        CNDatePickerView(node: node)
    }

    @ViewBuilder
    private func renderStepper(_ node: CNSwiftStepperNode) -> some View {
        CNStepperView(node: node)
    }

    @ViewBuilder
    private func renderRatingBar(_ node: CNSwiftRatingBarNode) -> some View {
        CNRatingBarView(node: node)
    }

    @ViewBuilder
    private func renderMenu(_ node: CNSwiftMenuNode) -> some View {
        Menu {
            ForEach(node.items) { item in
                Button(role: item.isDestructive ? .destructive : nil, action: item.onClick) {
                    if let icon = item.icon {
                        Label(item.title, systemImage: icon)
                    } else {
                        Text(item.title)
                    }
                }
                .disabled(!item.isEnabled)
            }
        } label: {
            if let trigger = node.triggerContent {
                CNNodeRenderer(node: trigger)
            } else {
                Label(node.title, systemImage: "ellipsis.circle")
                    .font(.system(size: 16, weight: .medium))
            }
        }
    }

    @ViewBuilder
    private func renderPager(_ node: CNSwiftPagerNode) -> some View {
        TabView(selection: Binding(get: { node.currentPage }, set: { node.onPageChange($0) })) {
            ForEach(0..<node.children.count, id: \.self) { index in
                CNNodeRenderer(node: node.children[index])
                    .tag(index)
            }
        }
        #if os(iOS)
        .tabViewStyle(PageTabViewStyle(indexDisplayMode: .automatic))
        #endif
    }

    @ViewBuilder
    private func renderSearchBar(_ node: CNSwiftSearchBarNode) -> some View {
        CNSearchBarView(node: node)
    }

    @ViewBuilder
    private func renderSnackbar(_ node: CNSwiftSnackbarNode) -> some View {
        HStack(spacing: 12) {
            Text(node.message)
                .font(.system(size: 14, weight: .medium))
                .foregroundColor(.white)
            Spacer()
            if let actionLabel = node.actionLabel, let onAction = node.onAction {
                Button(actionLabel, action: onAction)
                    .font(.system(size: 14, weight: .bold))
                    .foregroundColor(CNSwiftColor.accent.swiftUIColor)
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 12)
        .background(Color(red: 0.2, green: 0.2, blue: 0.22))
        .cornerRadius(10)
        .shadow(radius: 4)
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
    private func renderRangeSlider(_ node: CNSwiftRangeSliderNode) -> some View {
        CNRangeSliderView(node: node)
    }

    @ViewBuilder
    private func renderRadioButton(_ node: CNSwiftRadioButtonNode) -> some View {
        Button(action: node.onClick) {
            HStack(spacing: 8) {
                Image(systemName: node.isSelected ? "largecircle.fill.circle" : "circle")
                    .foregroundColor(node.isSelected ? node.selectedColor.swiftUIColor : .gray)
                    .font(.system(size: 20))
                if let label = node.label {
                    Text(label)
                        .foregroundColor(themeState.isDarkMode ? Color.white : Color.primary)
                        .font(.system(size: 15))
                }
            }
        }
        .disabled(!node.isEnabled)
    }

    @ViewBuilder
    private func renderChip(_ node: CNSwiftChipNode) -> some View {
        Button(action: node.onClick) {
            HStack(spacing: 6) {
                if let icon = node.icon {
                    Image(systemName: icon)
                        .font(.system(size: 13, weight: .semibold))
                }
                Text(node.text)
                    .font(.system(size: 13, weight: node.isSelected ? .bold : .medium))
            }
            .padding(.horizontal, 12)
            .padding(.vertical, 7)
            .background(node.isSelected ? CNSwiftColor.primary.swiftUIColor.opacity(0.18) : (themeState.isDarkMode ? Color.white.opacity(0.1) : Color.secondary.opacity(0.12)))
            .foregroundColor(node.isSelected ? CNSwiftColor.primary.swiftUIColor : (themeState.isDarkMode ? Color.white : Color.primary))
            .clipShape(Capsule())
            .overlay(
                Capsule()
                    .stroke(node.isSelected ? CNSwiftColor.primary.swiftUIColor : Color.clear, lineWidth: 1.2)
            )
        }
        .disabled(!node.isEnabled)
    }

    @ViewBuilder
    private func renderSegmentedButton(_ node: CNSwiftSegmentedButtonNode) -> some View {
        CNSegmentedControlView(node: node)
    }

    @ViewBuilder
    private func renderListItem(_ node: CNSwiftListItemNode) -> some View {
        let content = HStack(spacing: 12) {
            if let leading = node.leading {
                CNNodeRenderer(node: leading)
            }
            VStack(alignment: .leading, spacing: 4) {
                CNNodeRenderer(node: node.headline)
                if let supporting = node.supporting {
                    CNNodeRenderer(node: supporting)
                }
            }
            Spacer()
            if let trailing = node.trailing {
                CNNodeRenderer(node: trailing)
            }
        }
        .padding(.vertical, 10)
        .padding(.horizontal, 14)
        .background(themeState.isDarkMode ? Color(red: 0.12, green: 0.12, blue: 0.14) : Color.white)
        .cornerRadius(10)

        if let onClick = node.onClick {
            Button(action: onClick) {
                content
            }
            .buttonStyle(.plain)
        } else {
            content
        }
    }

    @ViewBuilder
    private func renderAccordion(_ node: CNSwiftAccordionNode) -> some View {
        CNAccordionView(node: node)
    }

    @ViewBuilder
    private func renderBanner(_ node: CNSwiftBannerNode) -> some View {
        VStack(alignment: .leading, spacing: 10) {
            Text(node.text)
                .font(.system(size: 14))
                .foregroundColor(themeState.isDarkMode ? .white : .primary)
            HStack(spacing: 12) {
                Spacer()
                if let sec = node.secondaryActionText, let onSec = node.onSecondaryAction {
                    Button(sec, action: onSec)
                        .font(.system(size: 13, weight: .semibold))
                }
                if let pri = node.primaryActionText, let onPri = node.onPrimaryAction {
                    Button(pri, action: onPri)
                        .font(.system(size: 13, weight: .bold))
                }
            }
        }
        .padding(14)
        .background(Color.accentColor.opacity(0.15))
        .cornerRadius(10)
    }

    @ViewBuilder
    private func renderTabRow(_ node: CNSwiftTabRowNode) -> some View {
        CNTabRowView(node: node)
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
    private func renderLazyGrid(_ node: CNSwiftLazyGridNode) -> some View {
        let columns = Array(repeating: GridItem(.flexible(), spacing: node.spacing), count: node.columnsCount)
        ScrollView(.vertical, showsIndicators: true) {
            LazyVGrid(columns: columns, spacing: node.spacing) {
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

    @ViewBuilder
    private func renderImage(_ node: CNSwiftImageNode) -> some View {
        switch node.source {
        case .sfSymbol(let systemName):
            Image(systemName: systemName)
                .resizable()
                .aspectRatio(contentMode: node.contentMode)
                .foregroundColor(node.tint?.swiftUIColor ?? (themeState.isDarkMode ? .white : .primary))

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
        let surfaceColor = themeState.isDarkMode ? Color(red: 0.12, green: 0.12, blue: 0.14) : Color.white
        let resolvedBg = node.backgroundColor.name == "surface" ? surfaceColor : node.backgroundColor.swiftUIColor

        CNNodeRenderer(node: node.content)
            .background(resolvedBg)
            .clipShape(RoundedRectangle(cornerRadius: 12))
            .shadow(color: themeState.isDarkMode ? Color.clear : Color.black.opacity(0.06), radius: node.elevation, x: 0, y: node.elevation / 2)
    }

    @ViewBuilder
    private func renderScaffold(_ node: CNSwiftScaffoldNode) -> some View {
        let bgColor = themeState.isDarkMode ? Color(red: 0.0, green: 0.0, blue: 0.0) : Color(red: 0.95, green: 0.95, blue: 0.97)
        NavigationStack {
            ZStack {
                bgColor.ignoresSafeArea()
                CNNodeRenderer(node: node.content)
            }
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

// MARK: - Interactive Sub-Views

struct CNDatePickerView: View {
    let node: CNSwiftDatePickerNode
    @State private var selectedDate: Date = Date()

    var body: some View {
        DatePicker(
            node.title,
            selection: $selectedDate,
            displayedComponents: [.date]
        )
        .disabled(!node.isEnabled)
        .onAppear {
            if node.timestampMs > 0 {
                selectedDate = Date(timeIntervalSince1970: node.timestampMs / 1000)
            }
        }
        .onChange(of: selectedDate) { newDate in
            node.onDateChange(newDate.timeIntervalSince1970 * 1000)
        }
    }
}

struct CNStepperView: View {
    let node: CNSwiftStepperNode
    @State private var value: Double = 0

    var body: some View {
        Stepper(
            node.label.isEmpty ? "\(Int(value))" : "\(node.label): \(Int(value))",
            value: $value,
            in: node.min...node.max,
            step: node.step
        )
        .disabled(!node.isEnabled)
        .onAppear {
            value = node.value
        }
        .onChange(of: value) { newValue in
            node.onValueChange(newValue)
        }
    }
}

struct CNRatingBarView: View {
    let node: CNSwiftRatingBarNode
    @State private var currentRating: Int = 0

    var body: some View {
        HStack(spacing: 6) {
            ForEach(1...node.maxRating, id: \.self) { star in
                Button(action: {
                    currentRating = star
                    node.onRatingChange(star)
                }) {
                    Image(systemName: star <= currentRating ? "star.fill" : "star")
                        .foregroundColor(star <= currentRating ? node.activeColor.swiftUIColor : Color.gray.opacity(0.4))
                        .font(.system(size: 22))
                }
                .disabled(!node.isEnabled)
            }
        }
        .onAppear {
            currentRating = node.rating
        }
    }
}

struct CNSearchBarView: View {
    let node: CNSwiftSearchBarNode
    @State private var text: String = ""

    var body: some View {
        HStack(spacing: 8) {
            Image(systemName: "magnifyingglass")
                .foregroundColor(.secondary)
            TextField(node.placeholder, text: $text)
                .onSubmit {
                    node.onSearch(text)
                }
            if !text.isEmpty {
                Button(action: {
                    text = ""
                    node.onQueryChange("")
                }) {
                    Image(systemName: "xmark.circle.fill")
                        .foregroundColor(.secondary)
                }
            }
        }
        .padding(10)
        .background(Color.secondary.opacity(0.1))
        .cornerRadius(10)
        .onAppear {
            text = node.query
        }
        .onChange(of: text) { newValue in
            node.onQueryChange(newValue)
        }
    }
}

struct CNAccordionView: View {
    @ObservedObject private var themeState = CNSwiftThemeState.shared
    let node: CNSwiftAccordionNode
    @State private var expanded: Bool = false

    var body: some View {
        DisclosureGroup(node.title, isExpanded: $expanded) {
            CNNodeRenderer(node: node.content)
                .padding(.top, 8)
        }
        .padding(14)
        .background(themeState.isDarkMode ? Color(red: 0.12, green: 0.12, blue: 0.14) : Color.white)
        .cornerRadius(10)
        .onAppear {
            expanded = node.isExpanded
        }
        .onChange(of: expanded) { newValue in
            node.onToggle(newValue)
        }
    }
}

struct CNTabRowView: View {
    let node: CNSwiftTabRowNode
    @State private var selectedIndex: Int = 0

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 8) {
                ForEach(0..<node.tabs.count, id: \.self) { index in
                    let isSelected = selectedIndex == index
                    Button(action: {
                        selectedIndex = index
                        node.onTabSelected(index)
                    }) {
                        Text(node.tabs[index])
                            .font(.system(size: 14, weight: isSelected ? .bold : .medium))
                            .padding(.horizontal, 14)
                            .padding(.vertical, 8)
                            .background(isSelected ? CNSwiftColor.primary.swiftUIColor : Color.clear)
                            .foregroundColor(isSelected ? .white : Color.primary)
                            .clipShape(Capsule())
                    }
                }
            }
            .padding(.horizontal, 12)
            .padding(.vertical, 6)
        }
        .onAppear {
            selectedIndex = node.selectedIndex
        }
    }
}

struct CNSegmentedControlView: View {
    let node: CNSwiftSegmentedButtonNode
    @State private var selectedIndex: Int = 0

    var body: some View {
        Picker("", selection: $selectedIndex) {
            ForEach(0..<node.items.count, id: \.self) { index in
                Text(node.items[index]).tag(index)
            }
        }
        .pickerStyle(.segmented)
        .onAppear {
            selectedIndex = node.selectedIndex
        }
        .onChange(of: selectedIndex) { newValue in
            node.onSelectIndex(newValue)
        }
    }
}

struct CNRangeSliderView: View {
    let node: CNSwiftRangeSliderNode
    @State private var minVal: Float = 0
    @State private var maxVal: Float = 1

    var body: some View {
        VStack(spacing: 6) {
            HStack {
                Text("\(Int(minVal * 100))%")
                    .font(.caption)
                    .foregroundColor(.secondary)
                Spacer()
                Text("\(Int(maxVal * 100))%")
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
            HStack(spacing: 12) {
                Slider(value: $minVal, in: node.min...maxVal)
                Slider(value: $maxVal, in: minVal...node.max)
            }
        }
        .onAppear {
            minVal = node.startValue
            maxVal = node.endValue
        }
        .onChange(of: minVal) { newValue in
            node.onValuesChange(newValue, maxVal)
        }
        .onChange(of: maxVal) { newValue in
            node.onValuesChange(minVal, newValue)
        }
    }
}

struct CNTextFieldView: View {
    @ObservedObject private var themeState = CNSwiftThemeState.shared
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
        .background(themeState.isDarkMode ? Color(red: 0.16, green: 0.16, blue: 0.18) : Color(red: 0.95, green: 0.95, blue: 0.97))
        .cornerRadius(8)
        .overlay(
            RoundedRectangle(cornerRadius: 8)
                .stroke(node.isError ? Color.red : Color.gray.opacity(0.3), lineWidth: 1)
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
