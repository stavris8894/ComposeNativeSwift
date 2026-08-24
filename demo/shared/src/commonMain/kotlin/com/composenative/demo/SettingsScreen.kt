package com.composenative.demo

import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

/**
 * Settings Screen showcasing grouped controls, switches, sliders, and dividers.
 */
class SettingsScreen : CNScreen() {
    var darkModeEnabled by mutableStateOf(false)
    var notificationsEnabled by mutableStateOf(true)
    var biometricAuthEnabled by mutableStateOf(true)
    var volumeLevel by mutableStateOf(0.75f)

    override fun build(): CNNode = Scaffold(
        topBar = TopAppBar(
            title = "Preferences & Settings",
            backgroundColor = CNColor.Surface
        ),
        content = LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("General", style = TextStyle.H5, color = CNColor.Primary)
            }

            // General Settings Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(14.dp),
                    elevation = 2.dp
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Dark Theme", style = TextStyle.BodyLarge)
                            Switch(
                                checked = darkModeEnabled,
                                onCheckedChange = { darkModeEnabled = it }
                            )
                        }

                        Divider()

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Push Notifications", style = TextStyle.BodyLarge)
                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = { notificationsEnabled = it }
                            )
                        }

                        Divider()

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Face ID / Touch ID", style = TextStyle.BodyLarge)
                            Switch(
                                checked = biometricAuthEnabled,
                                onCheckedChange = { biometricAuthEnabled = it }
                            )
                        }
                    }
                }
            }

            item {
                Text("Audio & Haptics", style = TextStyle.H5, color = CNColor.Primary)
            }

            // Audio Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(14.dp),
                    elevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Effects Volume", style = TextStyle.BodyLarge)
                            Text("${(volumeLevel * 100).toInt()}%", style = TextStyle.BodyLarge, fontWeight = FontWeight.Bold, color = CNColor.Primary)
                        }
                        Slider(
                            value = volumeLevel,
                            onValueChange = { volumeLevel = it },
                            valueRange = 0f..1f
                        )
                    }
                }
            }

            // App Info
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(14.dp),
                    elevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Engine", style = TextStyle.BodyMedium, color = CNColor.Gray)
                            Text("ComposeNativeSwift v1.0.0", style = TextStyle.BodyMedium, fontWeight = FontWeight.SemiBold)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("SwiftUI Rendering", style = TextStyle.BodyMedium, color = CNColor.Gray)
                            Text("Native Declarative Tree", style = TextStyle.BodyMedium, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    )
}
