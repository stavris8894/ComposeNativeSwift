// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "ComposeNativeSwift",
    platforms: [
        .iOS(.v16),
        .macOS(.v13),
        .tvOS(.v16),
        .watchOS(.v9)
    ],
    products: [
        .library(
            name: "ComposeNativeSwift",
            targets: ["ComposeNativeSwift"]
        ),
    ],
    dependencies: [],
    targets: [
        .target(
            name: "ComposeNativeSwift",
            dependencies: [],
            path: "Sources/ComposeNativeSwift"
        ),
        .testTarget(
            name: "ComposeNativeSwiftTests",
            dependencies: ["ComposeNativeSwift"],
            path: "Tests/ComposeNativeSwiftTests"
        ),
    ]
)
