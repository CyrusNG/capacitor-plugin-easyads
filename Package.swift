// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorPluginEasyads",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorPluginEasyads",
            targets: ["EasyAdsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "EasyAdsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/EasyAdsPlugin"),
        .testTarget(
            name: "EasyAdsPluginTests",
            dependencies: ["EasyAdsPlugin"],
            path: "ios/Tests/EasyAdsPluginTests")
    ]
)