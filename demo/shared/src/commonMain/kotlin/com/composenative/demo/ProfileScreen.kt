package com.composenative.demo

import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

/**
 * Profile Screen showcasing Cards, Custom Layouts, Dynamic Stats, and Buttons.
 */
class ProfileScreen : CNScreen() {
    var isFollowing by mutableStateOf(false)
    var followerCount by mutableStateOf(1420)

    private fun toggleFollow() {
        if (isFollowing) {
            followerCount--
            isFollowing = false
        } else {
            followerCount++
            isFollowing = true
        }
    }

    override fun build(): CNNode = Scaffold(
        topBar = TopAppBar(
            title = "Developer Profile",
            backgroundColor = CNColor.Surface
        ),
        content = LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Header Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(16.dp),
                    elevation = 3.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            url = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&auto=format&fit=crop&q=80",
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CNShape.Circle)
                                .border(CNBorder(3.dp, CNColor.Primary, CNShape.Circle))
                        )

                        Text("Elena Rostova", style = TextStyle.H4)
                        Text("Staff Mobile Architect", style = TextStyle.BodyMedium, color = CNColor.Gray)
                        Text(
                            "Building ultra-fast cross-platform applications with Kotlin Multiplatform & SwiftUI native components.",
                            style = TextStyle.BodyMedium,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Stats Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("$followerCount", style = TextStyle.H5, fontWeight = FontWeight.Bold)
                                Text("Followers", style = TextStyle.Caption)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("384", style = TextStyle.H5, fontWeight = FontWeight.Bold)
                                Text("Following", style = TextStyle.Caption)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("52", style = TextStyle.H5, fontWeight = FontWeight.Bold)
                                Text("Projects", style = TextStyle.Caption)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Actions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { toggleFollow() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .background(
                                        if (isFollowing) CNColor.Gray else CNColor.Primary,
                                        CNShape.RoundedCorner(10.dp)
                                    )
                            ) {
                                Text(
                                    if (isFollowing) "Following" else "Follow",
                                    color = CNColor.White,
                                    style = TextStyle.Button
                                )
                            }

                            Button(
                                onClick = { /* Send message */ },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .background(CNColor.SurfaceVariant, CNShape.RoundedCorner(10.dp))
                            ) {
                                Text("Message", color = CNColor.OnSurface, style = TextStyle.Button)
                            }
                        }
                    }
                }
            }

            // Skills & Tech Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(14.dp),
                    elevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Core Competencies", style = TextStyle.H5)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Badge(text = "Kotlin", backgroundColor = CNColor.Primary.copyWithAlpha(0.15f), contentColor = CNColor.Primary)
                            Badge(text = "SwiftUI", backgroundColor = CNColor.Secondary.copyWithAlpha(0.15f), contentColor = CNColor.Secondary)
                            Badge(text = "KMP", backgroundColor = CNColor.Success.copyWithAlpha(0.15f), contentColor = CNColor.Success)
                            Badge(text = "Jetpack Compose", backgroundColor = CNColor.Accent.copyWithAlpha(0.15f), contentColor = CNColor.Accent)
                        }
                    }
                }
            }
        }
    )
}
