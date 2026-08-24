import XCTest
@testable import ComposeNativeSwift

final class ComposeNativeSwiftTests: XCTestCase {

    func testColorHexParsing() {
        let blue = CNSwiftColor(hex: "#007AFF")
        XCTAssertEqual(blue.red, 0.0, accuracy: 0.01)
        XCTAssertEqual(blue.green, 122.0 / 255.0, accuracy: 0.01)
        XCTAssertEqual(blue.blue, 1.0, accuracy: 0.01)
        XCTAssertEqual(blue.alpha, 1.0, accuracy: 0.01)
    }

    func testNodeTreeConstruction() {
        var clicked = false
        let buttonContent = CNSwiftTextNode(text: "Submit")
        let button = CNSwiftButtonNode(
            onClick: { clicked = true },
            content: buttonContent
        )

        let column = CNSwiftColumnNode(
            spacing: 16,
            children: [
                CNSwiftTextNode(text: "Header"),
                button
            ]
        )

        XCTAssertEqual(column.children.count, 2)
        XCTAssertFalse(clicked)
        button.onClick()
        XCTAssertTrue(clicked)
    }

    func testDatePickerAndStepperNodes() {
        var selectedDateMs: Double = 0
        let datePicker = CNSwiftDatePickerNode(
            title: "Date of Birth",
            timestampMs: 1700000000000,
            onDateChange: { selectedDateMs = $0 }
        )
        XCTAssertEqual(datePicker.title, "Date of Birth")
        datePicker.onDateChange(1700005000000)
        XCTAssertEqual(selectedDateMs, 1700005000000)

        var stepperValue: Double = 5
        let stepper = CNSwiftStepperNode(
            value: stepperValue,
            onValueChange: { stepperValue = $0 },
            min: 0,
            max: 20,
            step: 1,
            label: "Developers"
        )
        XCTAssertEqual(stepper.value, 5)
        stepper.onValueChange(6)
        XCTAssertEqual(stepperValue, 6)
    }

    func testMenuAndRatingNodes() {
        var itemClicked = false
        let menuItem = CNSwiftMenuItem(
            title: "Settings",
            icon: "gear",
            isDestructive: false,
            isEnabled: true,
            onClick: { itemClicked = true }
        )
        let menu = CNSwiftMenuNode(title: "Options", items: [menuItem])
        XCTAssertEqual(menu.items.count, 1)
        menuItem.onClick()
        XCTAssertTrue(itemClicked)

        var currentRating = 3
        let ratingNode = CNSwiftRatingBarNode(
            rating: currentRating,
            maxRating: 5,
            onRatingChange: { currentRating = $0 }
        )
        XCTAssertEqual(ratingNode.rating, 3)
        ratingNode.onRatingChange(5)
        XCTAssertEqual(currentRating, 5)
    }

    func testPagerAndSearchBarNodes() {
        var page = 0
        let pager = CNSwiftPagerNode(
            isHorizontal: true,
            currentPage: page,
            onPageChange: { page = $0 },
            children: [
                CNSwiftTextNode(text: "Page 1"),
                CNSwiftTextNode(text: "Page 2")
            ]
        )
        XCTAssertEqual(pager.children.count, 2)
        pager.onPageChange(1)
        XCTAssertEqual(page, 1)

        var query = ""
        let search = CNSwiftSearchBarNode(
            query: query,
            onQueryChange: { query = $0 },
            placeholder: "Search components..."
        )
        search.onQueryChange("ComposeNative")
        XCTAssertEqual(query, "ComposeNative")
    }

    func testModifierApplierWithBlurAndMaterial() {
        let textNode = CNSwiftTextNode(
            text: "Vibrant Card",
            modifiers: [
                .blur(5),
                .material(type: .ultraThin, shape: .roundedCorner(radius: 12)),
                .haptic(type: .medium)
            ]
        )
        XCTAssertEqual(textNode.modifierElements.count, 3)
    }

    func testNavigationHostAndBackStack() {
        var backPopped = false
        let content = CNSwiftTextNode(text: "Detail Screen")
        let navHost = CNSwiftNavHostNode(
            activeRoute: "details",
            backStackCount: 2,
            currentTitle: "Product Detail",
            navBarStyle: "LiquidGlass",
            showBackButton: true,
            onPopBack: { backPopped = true },
            content: content
        )

        XCTAssertEqual(navHost.activeRoute, "details")
        XCTAssertEqual(navHost.backStackCount, 2)
        XCTAssertEqual(navHost.currentTitle, "Product Detail")
        XCTAssertTrue(navHost.showBackButton)
        XCTAssertFalse(backPopped)

        navHost.onPopBack()
        XCTAssertTrue(backPopped)
    }

    func testLiquidGlassNodeAndProperties() {
        let glass = CNSwiftLiquidGlassNode(
            properties: CNSwiftLiquidGlassProperties(
                blurRadius: 25,
                cornerRadius: 28,
                specularOpacity: 0.5
            ),
            content: CNSwiftTextNode(text: "Glass Content")
        )

        XCTAssertEqual(glass.properties.blurRadius, 25)
        XCTAssertEqual(glass.properties.cornerRadius, 28)
        XCTAssertEqual(glass.properties.specularOpacity, 0.5)
    }

    func testStateObserverUpdate() {
        class MockScreen: CNStateObservableScreen {
            var count = 0
            private var listeners: [() -> Void] = []

            func renderSwiftNode() -> CNRenderableNode {
                CNSwiftTextNode(text: "Count: \(count)")
            }

            func addStateListener(_ listener: @escaping () -> Void) -> () -> Void {
                listeners.append(listener)
                return { [weak self] in
                    self?.listeners.removeAll()
                }
            }

            func increment() {
                count += 1
                listeners.forEach { $0() }
            }

            func onDispose() {
                listeners.removeAll()
            }
        }

        let screen = MockScreen()
        let observer = CNStateObserver(screen: screen)

        guard let initialNode = observer.currentNode as? CNSwiftTextNode else {
            XCTFail("Node is not CNSwiftTextNode")
            return
        }
        XCTAssertEqual(initialNode.text, "Count: 0")

        let exp = expectation(description: "Wait for state change")
        screen.increment()

        DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) {
            if let updatedNode = observer.currentNode as? CNSwiftTextNode {
                XCTAssertEqual(updatedNode.text, "Count: 1")
                exp.fulfill()
            }
        }

        waitForExpectations(timeout: 1.0)
    }
}
