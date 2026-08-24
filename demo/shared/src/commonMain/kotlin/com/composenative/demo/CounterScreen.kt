package com.composenative.demo

import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

/**
 * Interactive Counter Screen written in Kotlin Compose.
 * Renders into 100% genuine native SwiftUI components on iOS and Jetpack Compose on Android!
 */
class CounterScreen : CNScreen() {
    var count by mutableStateOf(0)
    var step by mutableStateOf(1)

    override fun build(): CNNode = Scaffold(
        topBar = TopAppBar(
            title = "Compose Native Counter",
            backgroundColor = CNColor.Surface,
            contentColor = CNColor.OnSurface
        ),
        content = Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            add(
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(16.dp),
                    elevation = 4.dp,
                    backgroundColor = CNColor.Surface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        add(
                            Text(
                                text = "Native SwiftUI from Kotlin",
                                style = TextStyle.H5,
                                color = CNColor.Primary
                            )
                        )
                        add(
                            Text(
                                text = "Count",
                                style = TextStyle.Caption
                            )
                        )
                        add(
                            Text(
                                text = "$count",
                                style = TextStyle(
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = when {
                                        count > 0 -> CNColor.Success
                                        count < 0 -> CNColor.Error
                                        else -> CNColor.OnSurface
                                    }
                                )
                            )
                        )
                    }
                }
            )

            add(
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    add(
                        Button(
                            onClick = { count -= step },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .background(CNColor.Error, CNShape.RoundedCorner(12.dp))
                        ) {
                            Text("-$step", color = CNColor.White, style = TextStyle.Button)
                        }
                    )

                    add(
                        Button(
                            onClick = { count = 0 },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .background(CNColor.Gray, CNShape.RoundedCorner(12.dp))
                        ) {
                            Text("Reset", color = CNColor.White, style = TextStyle.Button)
                        }
                    )

                    add(
                        Button(
                            onClick = { count += step },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .background(CNColor.Success, CNShape.RoundedCorner(12.dp))
                        ) {
                            Text("+$step", color = CNColor.White, style = TextStyle.Button)
                        }
                    )
                }
            )

            add(
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(12.dp),
                    elevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        add(
                            Text(
                                text = "Step Size: $step",
                                style = TextStyle.BodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        add(
                            Slider(
                                value = step.toFloat(),
                                onValueChange = { step = it.toInt().coerceAtLeast(1) },
                                valueRange = 1f..10f,
                                steps = 9
                            )
                        )
                    }
                }
            )
        }
    )
}
