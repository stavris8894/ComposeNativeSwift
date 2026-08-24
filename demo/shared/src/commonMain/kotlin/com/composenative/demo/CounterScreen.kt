package com.composenative.demo

import com.composenative.demo.viewmodels.CounterViewModel
import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

/**
 * Interactive Counter Screen using CounterViewModel in Kotlin Common.
 */
class CounterScreen(
    viewModel: CounterViewModel
) : CNScreenWithViewModel<CounterViewModel>(viewModel) {
    constructor() : this(CounterViewModel())

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
                                text = "Kotlin ViewModel Counter",
                                style = TextStyle.H5,
                                color = CNColor.Primary
                            )
                        )
                        add(
                            Text(
                                text = "Current Value",
                                style = TextStyle.Caption
                            )
                        )
                        add(
                            Text(
                                text = "${viewModel.count}",
                                style = TextStyle(
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = when {
                                        viewModel.count > 0 -> CNColor.Success
                                        viewModel.count < 0 -> CNColor.Error
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
                            onClick = { viewModel.decrement() },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .background(CNColor.Error, CNShape.RoundedCorner(12.dp))
                                .haptic(CNHapticType.Medium)
                        ) {
                            Text("-${viewModel.step}", color = CNColor.White, style = TextStyle.Button)
                        }
                    )

                    add(
                        Button(
                            onClick = { viewModel.reset() },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .background(CNColor.Gray, CNShape.RoundedCorner(12.dp))
                                .haptic(CNHapticType.Light)
                        ) {
                            Text("Reset", color = CNColor.White, style = TextStyle.Button)
                        }
                    )

                    add(
                        Button(
                            onClick = { viewModel.increment() },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .background(CNColor.Success, CNShape.RoundedCorner(12.dp))
                                .haptic(CNHapticType.Medium)
                        ) {
                            Text("+${viewModel.step}", color = CNColor.White, style = TextStyle.Button)
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
                                text = "Step Size: ${viewModel.step}",
                                style = TextStyle.BodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        add(
                            Slider(
                                value = viewModel.step.toFloat(),
                                onValueChange = { viewModel.updateStep(it.toInt()) },
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
