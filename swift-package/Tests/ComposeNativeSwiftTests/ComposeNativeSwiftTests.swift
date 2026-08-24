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
