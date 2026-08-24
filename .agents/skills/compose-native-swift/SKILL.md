---
name: compose-native-swift
description: >-
  Expert guide for writing, converting, and integrating Kotlin Multiplatform Compose UI
  into 100% native SwiftUI components using ComposeNativeSwift with zero configuration in Swift.
---

# ComposeNativeSwift: AI Agent Guide & Runbook

This skill equips AI assistants to write Kotlin Multiplatform (KMP) code that automatically renders as **100% genuine native SwiftUI components on iOS** (and Jetpack Compose on Android) with minimal setup.

---

## 1. Core Architecture

`ComposeNativeSwift` avoids canvas-based drawing (e.g. Skiko canvas) on iOS. Instead, it translates declarative Kotlin Compose UI trees directly into genuine SwiftUI native views (`SwiftUI.Text`, `SwiftUI.Button`, `SwiftUI.VStack`, `SwiftUI.HStack`, `SwiftUI.ZStack`, `SwiftUI.TextField`, `SwiftUI.Toggle`, `SwiftUI.Slider`, `SwiftUI.List`, `SwiftUI.AsyncImage`, etc.).

### Dataflow & Reactive Loop

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

## 2. Writing Screens in Kotlin

Developers write standard Compose-like code by subclassing `CNScreen` and implementing `build()`:

```kotlin
package com.myproject.ui

import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

class CounterScreen : CNScreen() {
    var count by mutableStateOf(0)

    override fun build(): CNNode = Scaffold(
        topBar = TopAppBar(title = "Native Counter"),
        content = Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            add(
                Text(
                    text = "Count: $count",
                    style = TextStyle.H4,
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

---

## 3. Minimal Configuration in Swift (iOS App)

In the iOS project, add the Swift Package `ComposeNativeSwift` and display any shared Kotlin screen in **1 line of SwiftUI**:

```swift
import SwiftUI
import ComposeNativeSwift
import SharedApp // or your KMP framework name

struct ContentView: View {
    var body: some View {
        ComposeNativeView(screen: CounterScreen())
    }
}
```

---

## 4. Component Mapping Reference

| Kotlin / Compose API | ComposeNativeSwift Component | Native SwiftUI View |
| :--- | :--- | :--- |
| `Column { ... }` | `Column(modifier) { ... }` | `SwiftUI.VStack` |
| `Row { ... }` | `Row(modifier) { ... }` | `SwiftUI.HStack` |
| `Box { ... }` | `Box(modifier) { ... }` | `SwiftUI.ZStack` |
| `Text("...")` | `Text("...", style)` | `SwiftUI.Text("...")` |
| `Button(onClick) { ... }` | `Button(onClick) { ... }` | `SwiftUI.Button(action)` |
| `TextField(value, onValueChange)` | `TextField(...)` | `SwiftUI.TextField(...)` |
| `OutlinedTextField(...)` | `OutlinedTextField(...)` | `SwiftUI.TextField + overlay` |
| `Switch(checked, onCheckedChange)` | `Switch(...)` | `SwiftUI.Toggle(...)` |
| `Slider(value, onValueChange)` | `Slider(...)` | `SwiftUI.Slider(...)` |
| `LazyColumn { items(list) }` | `LazyColumn { items(list) }` | `SwiftUI.ScrollView + LazyVStack` |
| `LazyRow { items(list) }` | `LazyRow { items(list) }` | `SwiftUI.ScrollView + LazyHStack` |
| `AsyncImage(url)` | `AsyncImage(url)` | `SwiftUI.AsyncImage(url)` |
| `Icon(Icons.Add)` | `Icon(Icons.Add)` | `SwiftUI.Image(systemName:)` |
| `Card { ... }` | `Card(elevation) { ... }` | `SwiftUI.Card + shadow` |
| `Scaffold(topBar) { ... }` | `Scaffold(...)` | `SwiftUI.NavigationStack` |
| `CircularProgressIndicator()` | `CircularProgressIndicator()` | `SwiftUI.ProgressView()` |
| `Divider()` | `Divider()` | `SwiftUI.Divider()` |
| `Spacer(Modifier.height(16.dp))` | `Spacer(Modifier.height(16.dp))`| `SwiftUI.Spacer()` |

---

## 5. Modifier Guide

Chain modifiers identically to Jetpack Compose:

```kotlin
Modifier
    .fillMaxWidth()
    .height(60.dp)
    .padding(horizontal = 16.dp, vertical = 8.dp)
    .background(CNColor.Primary, CNShape.RoundedCorner(12.dp))
    .shadow(elevation = 4.dp)
    .clickable { /* action */ }
```

Supported modifiers:
- `padding(all / horizontal, vertical / start, top, end, bottom)`
- `fillMaxWidth()`, `fillMaxHeight()`, `fillMaxSize()`
- `width(dp)`, `height(dp)`, `size(dp)`, `size(width, height)`
- `background(color, shape)`
- `clip(shape)`
- `cornerRadius(radius)`
- `border(width, color, shape)`
- `shadow(elevation, shape)`
- `clickable(enabled, onClick)`
- `alpha(fraction)`
- `offset(x, y)`
- `weight(fraction)`
- `aspectRatio(ratio)`

---

## 6. How to Convert Existing Jetpack Compose Code

When converting an existing `@Composable` function:
1. Wrap the screen in a class extending `CNScreen()`.
2. Replace mutable state declarations: `var foo by mutableStateOf(...)`.
3. In `build(): CNNode`, return the root composable (`Scaffold`, `Column`, `Box`, `LazyColumn`).
4. Replace Jetpack Compose imports with:
   ```kotlin
   import com.composenative.swift.*
   import com.composenative.swift.components.*
   import com.composenative.swift.core.*
   ```
5. Inside container scopes (`Column`, `Row`, `Box`), call `add(...)` or `+` to add child nodes.

---

## 7. Troubleshooting & Rules

1. **Reactivity**: Always use `mutableStateOf(...)` inside `CNScreen`. It automatically hooks into `screen.addListener` and tells SwiftUI's `ObservableObject` to trigger a re-render.
2. **Lambdas**: For UI event handlers (`onClick`, `onValueChange`), ensure captured variables are modified on the main thread or via `mutableStateOf`.
3. **Async Tasks**: Use `screenScope.launch { ... }` inside `CNScreen` for coroutines (e.g. network requests, database operations). When state variables are updated, UI refreshes automatically.
