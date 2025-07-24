// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFloatingBubble",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorFloatingBubble",
            targets: ["FloatingBubblePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "FloatingBubblePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/FloatingBubblePlugin"),
        .testTarget(
            name: "FloatingBubblePluginTests",
            dependencies: ["FloatingBubblePlugin"],
            path: "ios/Tests/FloatingBubblePluginTests")
    ]
)
