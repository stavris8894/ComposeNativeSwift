package com.composenative.demo

import com.composenative.demo.viewmodels.SettingsViewModel
import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

/**
 * Settings Screen using SettingsViewModel in Kotlin Common.
 */
class SettingsScreen(
    viewModel: SettingsViewModel = SettingsViewModel()
) : CNScreenWithViewModel<SettingsViewModel>(viewModel) {

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
                                checked = viewModel.darkModeEnabled,
                                onCheckedChange = { viewModel.toggleDarkMode(it) }
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
                                checked = viewModel.notificationsEnabled,
                                onCheckedChange = { viewModel.toggleNotifications(it) }
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
                                checked = viewModel.biometricAuthEnabled,
                                onCheckedChange = { viewModel.toggleBiometrics(it) }
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
                            Text("${(viewModel.volumeLevel * 100).toInt()}%", style = TextStyle.BodyLarge, fontWeight = FontWeight.Bold, color = CNColor.Primary)
                        }
                        Slider(
                            value = viewModel.volumeLevel,
                            onValueChange = { viewModel.updateVolume(it) },
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
