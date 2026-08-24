# ComposeNativeSwift 🚀

> **Translate Kotlin Multiplatform Compose UI into 100% Genuine Native SwiftUI Views on iOS with Zero Friction.**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.10-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Swift](https://img.shields.io/badge/Swift-5.9%2B-orange.svg?logo=swift)](https://developer.apple.com/swift/)
[![SwiftUI](https://img.shields.io/badge/SwiftUI-iOS%2016%2B-blue.svg?logo=apple)](https://developer.apple.com/xcode/swiftui/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Android-green.svg?logo=android)](https://developer.android.com/jetpack/compose)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

---

## 🌟 Why ComposeNativeSwift?

Standard Compose Multiplatform (CMP) on iOS renders onto a custom Skiko (Metal/OpenGL) canvas. While useful, it loses genuine iOS platform advantages.

**ComposeNativeSwift** takes a fundamentally different approach:
- 📱 **100% True Native SwiftUI Components**: Text is `SwiftUI.Text`, Buttons are `SwiftUI.Button`, Lists are `SwiftUI.ScrollView + LazyVStack`, Text fields are `SwiftUI.TextField` with native iOS autocorrect and keyboards.
- ⚡ **Zero Canvas Overhead**: Ultra-fast native rendering with 120Hz Apple ProMotion support.
- ♿ **Full iOS Accessibility & VoiceOver**: Works out of the box with zero extra code.
- 🎯 **Minimal / Zero Configuration in Swift**: Embed any Kotlin screen in **exactly 1 line of SwiftUI**: `ComposeNativeView(screen: MyScreen())`.
- 🔄 **Reactive State Synchronization**: Kotlin `mutableStateOf` bridges directly into SwiftUI's `@Published` / `ObservableObject` reactive lifecycle.
- 🤖 **Built-in AI Assistant Skill**: Pre-configured with an Antigravity AI skill for automatic code generation and conversion.

---

## 🏗️ Architecture

```
┌────────────────────────────────────────────────────────┐
│               Kotlin Multiplatform (Shared)            │
│  - Screen (CNScreen)                                   │
│  - State (var count by mutableStateOf(0))              │
│  - Composables (Column, Row, Text, Button, TextField)  │
│  - Virtual Node Tree (CNNode)                          │
└───────────────────────────┬────────────────────────────┘
                            │ (Reactive Listener Callback)
                            ▼
┌────────────────────────────────────────────────────────┐
│                   Swift / SwiftUI (iOS)                │
│  - ComposeNativeView(screen: MyScreen())               │
│  - CNStateObserver (ObservableObject / @Published)     │
│  - CNNodeRenderer                                      │
│  - 100% Native SwiftUI Views (VStack, Text, Button...) │
└────────────────────────────────────────────────────────┘
```

---

## 📦 Project Structure

```
ComposeNativeSwift/
├── compose-native-core/      # KMP Library module (Kotlin declarative DSL & node tree)
├── swift-package/            # Swift Package (Pure SwiftUI native rendering engine)
├── demo/
│   ├── shared/               # Shared KMP demo screens (Counter, Form, Feed, Profile, Settings)
│   ├── iosApp/               # SwiftUI iOS application demo
│   └── androidApp/           # Jetpack Compose Android application demo
├── .agents/skills/           # Antigravity AI skill for future AI pair programming
└── README.md
```

---

## 🚀 Quick Start

### 1. Write your Screen in Kotlin (Shared KMP)

Subclass `CNScreen` and return your UI tree from `build()`:

```kotlin
package com.composenative.demo

import com.composenative.swift.*
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
                    style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold),
                    color = if (count >= 0) CNColor.Success else CNColor.Error
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
                            modifier = Modifier.weight(1f).height(48.dp).background(CNColor.Error)
                        ) {
                            Text("Decrement", color = CNColor.White)
                        }
                    )
                    add(
                        Button(
                            onClick = { count++ },
                            modifier = Modifier.weight(1f).height(48.dp).background(CNColor.Success)
                        ) {
                            Text("Increment", color = CNColor.White)
                        }
                    )
                }
            )
        }
    )
}
```

### 2. Display in SwiftUI (iOS App)

In your iOS SwiftUI project, import `ComposeNativeSwift` and your shared framework:

```swift
import SwiftUI
import ComposeNativeSwift
import SharedApp

struct ContentView: View {
    var body: some View {
        // Exactly 1 line of code!
        ComposeNativeView(screen: CounterScreen())
    }
}
```

That's it! When you tap Increment/Decrement in SwiftUI, it invokes the Kotlin lambda, updates Kotlin state, and re-renders the SwiftUI view instantly.

---

## 📋 Component Mapping Reference

| Jetpack Compose API | ComposeNative Component | SwiftUI Native View |
| :--- | :--- | :--- |
| `Column { ... }` | `Column { ... }` | `SwiftUI.VStack` |
| `Row { ... }` | `Row { ... }` | `SwiftUI.HStack` |
| `Box { ... }` | `Box { ... }` | `SwiftUI.ZStack` |
| `Text("Hello")` | `Text("Hello")` | `SwiftUI.Text("Hello")` |
| `Button(onClick) { ... }` | `Button(onClick) { ... }` | `SwiftUI.Button(action)` |
| `TextField(value, onValueChange)` | `TextField(...)` | `SwiftUI.TextField(...)` |
| `OutlinedTextField(...)` | `OutlinedTextField(...)` | `SwiftUI.TextField + RoundedBorder` |
| `Switch(checked, onCheckedChange)` | `Switch(...)` | `SwiftUI.Toggle(...)` |
| `Slider(value, onValueChange)` | `Slider(...)` | `SwiftUI.Slider(...)` |
| `LazyColumn { items(list) }` | `LazyColumn { items(list) }` | `SwiftUI.ScrollView { LazyVStack }` |
| `LazyRow { items(list) }` | `LazyRow { items(list) }` | `SwiftUI.ScrollView { LazyHStack }` |
| `AsyncImage(url)` | `AsyncImage(url)` | `SwiftUI.AsyncImage(url)` |
| `Icon(Icons.Add)` | `Icon(Icons.Add)` | `SwiftUI.Image(systemName:)` |
| `Card { ... }` | `Card(elevation) { ... }` | `SwiftUI.Card + shadow` |
| `Scaffold(topBar) { ... }` | `Scaffold(...)` | `SwiftUI.NavigationStack` |
| `CircularProgressIndicator()` | `CircularProgressIndicator()` | `SwiftUI.ProgressView()` |
| `Divider()` | `Divider()` | `SwiftUI.Divider()` |
| `Spacer(Modifier.height(16.dp))` | `Spacer(Modifier.height(16.dp))`| `SwiftUI.Spacer()` |

---

## 🎨 Modifiers System

Chaining modifiers matches standard Jetpack Compose:

```kotlin
Modifier
    .fillMaxWidth()
    .height(52.dp)
    .padding(horizontal = 16.dp, vertical = 8.dp)
    .background(CNColor.Primary, CNShape.RoundedCorner(12.dp))
    .shadow(elevation = 4.dp)
    .clickable { /* Handle Tap */ }
```

### Supported Modifiers
- **Sizing**: `fillMaxWidth()`, `fillMaxHeight()`, `fillMaxSize()`, `width(dp)`, `height(dp)`, `size(dp)`, `aspectRatio(ratio)`
- **Spacing**: `padding(all)`, `padding(horizontal, vertical)`, `padding(start, top, end, bottom)`
- **Styling**: `background(color, shape)`, `clip(shape)`, `cornerRadius(radius)`, `border(width, color)`, `shadow(elevation)`
- **Interactivity**: `clickable(onClick)`
- **Layout**: `offset(x, y)`, `alpha(opacity)`, `weight(fraction)`

---

## 🛠️ Installation & Setup

### Adding to Kotlin Multiplatform (`build.gradle.kts`)

In your shared KMP module:

```kotlin
kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "SharedApp"
            isStatic = true
            export(project(":compose-native-core"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":compose-native-core"))
        }
    }
}
```

### Adding to iOS (`Package.swift` or Xcode SPM)

In your iOS app `Package.swift` or Xcode *File > Add Package Dependencies*:

```swift
dependencies: [
    .package(path: "../swift-package") // or remote git repository URL
]
```

---

## 📱 Running the Demo

### Run JVM & Unit Tests
```bash
./gradlew check
```

### Run Swift Package Unit Tests
```bash
cd swift-package && swift test
```

### Compile iOS Simulator Target
```bash
./gradlew :demo:shared:compileKotlinIosSimulatorArm64
```

---

## 🚀 CI/CD & Automated Publishing

ComposeNativeSwift includes complete GitHub Actions workflows for multiplatform publishing across Android, JVM, and iOS:

### 1. Workflows
- **[CI Workflow](.github/workflows/ci.yml)** (`ci.yml`): Runs on every push and PR to `main`. Executes JVM unit tests, Android library compilation, linting, and Swift Package tests on macOS.
- **[Maven Multiplatform Publisher](.github/workflows/publish-maven.yml)** (`publish-maven.yml`): Publishes Android AARs, JVM JARs, and iOS Kotlin Native Klibs to **Maven Central** (Sonatype) and **GitHub Packages**.
- **[iOS XCFramework & SPM Publisher](.github/workflows/publish-ios-spm.yml)** (`publish-ios-spm.yml`): Compiles multi-architecture `ComposeNativeCore.xcframework` (arm64, simulator arm64, x64), computes the SHA-256 checksum, uploads binary archives to GitHub Releases, and verifies SPM integration.

### 2. Required GitHub Secrets
To configure automated releases in your GitHub repository settings (`Settings -> Secrets and variables -> Actions`):

| Secret | Purpose |
| :--- | :--- |
| `OSSRH_USERNAME` | Sonatype / Maven Central account username |
| `OSSRH_PASSWORD` | Sonatype / Maven Central token or password |
| `SIGNING_KEY` | Armored PGP private key for artifact signing |
| `SIGNING_PASSWORD` | Passphrase for the PGP private key |
| `GITHUB_TOKEN` | Automatically supplied by GitHub Actions for GitHub Packages and Release asset creation |

### 3. Manual Publishing via CLI
```bash
# Build multi-architecture XCFramework for iOS distribution
./gradlew :compose-native-core:assembleComposeNativeCoreReleaseXCFramework

# Publish to Maven Local
./gradlew :compose-native-core:publishToMavenLocal

# Publish to Maven Central (staging)
./gradlew :compose-native-core:publishAllPublicationsToSonatypeRepository -Pversion=1.0.0
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

