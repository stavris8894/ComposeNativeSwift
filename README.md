# ComposeNativeSwift 🚀

> **Translate Kotlin Multiplatform Compose UI into 100% Genuine Native SwiftUI Views on iOS with Zero Friction.**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.10-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Swift](https://img.shields.io/badge/Swift-5.9%2B-orange.svg?logo=swift)](https://developer.apple.com/swift/)
[![SwiftUI](https://img.shields.io/badge/SwiftUI-iOS%2016%2B-blue.svg?logo=apple)](https://developer.apple.com/xcode/swiftui/)
[![CI](https://github.com/stavris8894/ComposeNativeSwift/actions/workflows/ci.yml/badge.svg)](https://github.com/stavris8894/ComposeNativeSwift/actions/workflows/ci.yml)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

---

## 🌟 Overview

Unlike standard Compose Multiplatform (which draws on a canvas via Skiko/OpenGL), **ComposeNativeSwift** translates your Compose UI hierarchy directly into **100% genuine native SwiftUI views**:

- 📱 **True Native Views**: `Text` ➔ `SwiftUI.Text`, `Button` ➔ `SwiftUI.Button`, `TextField` ➔ `SwiftUI.TextField` (with iOS autocorrect, secure text entry, and system keyboards).
- ⚡ **Zero Canvas Overhead**: Fluid 120 FPS ProMotion animations with native battery efficiency.
- ♿ **Native iOS Accessibility**: VoiceOver and Dynamic Type work automatically.
- 🌓 **Material 3 & Dark Theme**: Built-in `CNTheme` engine that automatically adapts between Light and Dark mode.
- 🎯 **1 Line of SwiftUI**: Render any shared screen with `ComposeNativeView(screen: MyScreen())`.

---

## 📦 Installation

### 1. Kotlin Multiplatform (`build.gradle.kts`)

Add the dependency to your shared module:

```kotlin
repositories {
    mavenCentral()
}

kotlin {
    // Export framework for iOS
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "SharedApp"
            isStatic = true
            export("com.composenative.swift:compose-native-core:1.0.0")
        }
    }

    sourceSets {
        commonMain.dependencies {
            api("com.composenative.swift:compose-native-core:1.0.0")
        }
    }
}
```

### 2. iOS App (Swift Package Manager)

In Xcode, go to **File > Add Package Dependencies...** and enter:

```
https://github.com/stavris8894/ComposeNativeSwift.git
```

Or add it to your `Package.swift`:

```swift
dependencies: [
    .package(url: "https://github.com/stavris8894/ComposeNativeSwift.git", from: "1.0.0")
]
```

---

## 🚀 Quick Start

### 1. Define your Screen in Kotlin (`commonMain`)

Subclass `CNScreen` and write familiar Compose declarative code:

```kotlin
package com.example.shared

import com.composenative.swift.components.*
import com.composenative.swift.core.*

class CounterScreen : CNScreen() {
    var count by mutableStateOf(0)

    override fun build(): CNNode = Scaffold(
        topBar = TopAppBar(title = "ComposeNative Counter"),
        content = Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            add(
                Text(
                    text = "Count: $count",
                    style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                    color = if (count >= 0) CNColor.Primary else CNColor.Error
                )
            )
            add(
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    add(
                        Button(
                            onClick = { count-- },
                            modifier = Modifier.weight(1f).height(48.dp)
                        ) {
                            Text("Decrement")
                        }
                    )
                    add(
                        Button(
                            onClick = { count++ },
                            modifier = Modifier.weight(1f).height(48.dp)
                        ) {
                            Text("Increment")
                        }
                    )
                }
            )
        }
    )
}
```

### 2. Display in SwiftUI (`iOS App`)

Display the Kotlin screen in exactly **1 line of SwiftUI**:

```swift
import SwiftUI
import ComposeNativeSwift
import SharedApp

struct ContentView: View {
    var body: some View {
        ComposeNativeView(screen: CounterScreen())
    }
}
```

State changes in Kotlin automatically notify SwiftUI to re-render the native view tree reactively!

---

## 🧩 Component Catalog

ComposeNativeSwift supports all core Compose UI & Material 3 components:

| Category | Kotlin Multiplatform API | SwiftUI Native Mapping |
| :--- | :--- | :--- |
| **Layouts** | `Column`, `Row`, `Box`, `FlowRow` | `VStack`, `HStack`, `ZStack`, wrap layouts |
| **Typography** | `Text(text, style, maxLines)` | `SwiftUI.Text` with font weights & colors |
| **Buttons** | `Button`, `OutlinedButton`, `IconButton`, `ExtendedFloatingActionButton` | `SwiftUI.Button` with native styling |
| **Inputs** | `TextField`, `OutlinedTextField`, `Switch`, `Slider`, `RangeSlider` | `SwiftUI.TextField`, `Toggle`, `Slider`, dual-thumb range |
| **Selection** | `FilterChip`, `AssistChip`, `SegmentedButtonRow`, `RadioButton` | SwiftUI Capsule Chips, `Picker(.segmented)`, Radio Groups |
| **Lists & Grids** | `LazyColumn`, `LazyRow`, `LazyVerticalGrid`, `ListItem` | `ScrollView + LazyVStack / LazyVGrid`, Grouped Cells |
| **Navigation** | `Scaffold`, `TopAppBar`, `TabRow`, `NavigationBar` | `NavigationStack`, Navigation Title, TabBar |
| **Containers** | `Card`, `Surface`, `Accordion`, `Banner` | Rounded card elevation, `DisclosureGroup`, Notification banners |
| **Feedback** | `CircularProgressIndicator`, `LinearProgressIndicator`, `Badge`, `AlertDialog`, `ModalBottomSheet` | `ProgressView`, Badge Capsule, `.alert()`, `.sheet()` |

---

## 🌓 Theme & Dark Mode

ComposeNativeSwift includes full Material 3 ColorScheme support:

```kotlin
// Define theme in Kotlin
val lightColors = lightColorScheme(
    primary = CNColor.fromHex("#007AFF"),
    secondary = CNColor.fromHex("#5856D6"),
    surface = CNColor.White
)

val darkColors = darkColorScheme(
    primary = CNColor.fromHex("#0A84FF"),
    secondary = CNColor.fromHex("#5E5CE6"),
    surface = CNColor.fromHex("#1C1C1E")
)
```

In SwiftUI, theme colors seamlessly adapt to the iOS system appearance or explicit dark mode overrides:

```swift
ComposeNativeView(screen: MyScreen())
    .preferredColorScheme(isDarkMode ? .dark : .light)
```

---

## 🎨 Modifier Reference

ComposeNativeSwift modifiers use familiar Compose syntax:

```kotlin
Modifier
    .fillMaxWidth()
    .height(52.dp)
    .padding(horizontal = 16.dp, vertical = 8.dp)
    .background(CNColor.Primary, CNShape.RoundedCorner(12.dp))
    .shadow(elevation = 4.dp)
    .clickable { /* handle tap */ }
```

- **Sizing**: `fillMaxWidth()`, `fillMaxHeight()`, `fillMaxSize()`, `width(dp)`, `height(dp)`, `size(dp)`, `aspectRatio(ratio)`, `weight(fraction)`
- **Spacing**: `padding(all)`, `padding(horizontal, vertical)`, `padding(start, top, end, bottom)`
- **Styling**: `background(color, shape)`, `clip(shape)`, `cornerRadius(radius)`, `border(width, color)`, `shadow(elevation)`
- **Interactivity**: `clickable { ... }`, `alpha(opacity)`, `offset(x, y)`

---

## 🛠️ Repository & Testing

```bash
# Clone the repository
git clone https://github.com/stavris8894/ComposeNativeSwift.git
cd ComposeNativeSwift

# Run JVM & Android check
./gradlew check

# Run Swift Package tests
cd swift-package && swift test

# Build iOS multi-architecture XCFramework
./gradlew :compose-native-core:assembleComposeNativeCoreReleaseXCFramework
```

---

## 📄 License

```
Copyright 2026 ComposeNativeSwift Contributors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
```
