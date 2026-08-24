package com.composenative.demo

import com.composenative.demo.viewmodels.NavigationViewModel
import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*
import com.composenative.swift.navigation.*

data class TechItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: String,
    val icon: String,
    val tag: String
)

/**
 * Multi-Screen Navigation Demo using NavigationViewModel in Kotlin Common.
 */
class NavigationDemoScreen(
    viewModel: NavigationViewModel
) : CNScreenWithViewModel<NavigationViewModel>(viewModel) {
    constructor() : this(NavigationViewModel())

    override fun build(): CNNode = NavHost(
        navController = viewModel.navController,
        startDestination = "catalog"
    ) {
        // =====================================================================
        // Screen 1: Product Catalog
        // =====================================================================
        composable(
            route = "catalog",
            title = "Liquid Glass Store 🪐",
            navBarStyle = CNNavBarStyle.LiquidGlass
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        text = "Next-Gen Spatial Catalog",
                        style = TextStyle.H4,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    Text(
                        text = "Powered entirely by Kotlin NavigationViewModel with zero state in Swift.",
                        style = TextStyle.BodyMedium,
                        color = CNColor.Gray
                    )
                }

                items(viewModel.catalogItems) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .liquidGlass(
                                blurRadius = 24.dp,
                                tint = CNColor.White.copyWithAlpha(0.14f),
                                borderHighlight = CNColor.White.copyWithAlpha(0.4f),
                                cornerRadius = 18.dp
                            )
                            .clickable {
                                viewModel.selectItem(item.id)
                            }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            add(
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    add(
                                        Icon(
                                            icon = item.icon,
                                            tint = CNColor.Primary,
                                            size = 32.dp
                                        )
                                    )
                                    add(
                                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                            add(Text(item.title, style = TextStyle.BodyLarge, fontWeight = FontWeight.Bold))
                                            add(Text(item.subtitle, style = TextStyle.Caption, color = CNColor.Gray))
                                            add(Text(item.price, style = TextStyle.BodyMedium, color = CNColor.Primary, fontWeight = FontWeight.SemiBold))
                                        }
                                    )
                                }
                            )
                            add(
                                Badge(
                                    text = item.tag,
                                    backgroundColor = CNColor.Primary.copyWithAlpha(0.15f),
                                    contentColor = CNColor.Primary
                                )
                            )
                        }
                    }
                }
            }
        }

        // =====================================================================
        // Screen 2: Product Detail
        // =====================================================================
        composable(
            route = "details",
            title = "Device Specification",
            navBarStyle = CNNavBarStyle.LiquidGlass
        ) {
            val item = viewModel.catalogItems.find { it.id == viewModel.selectedItemId } ?: viewModel.catalogItems.first()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Hero Card with Liquid Glass
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .liquidGlass(
                                blurRadius = 28.dp,
                                tint = CNColor.White.copyWithAlpha(0.18f),
                                borderHighlight = CNColor.White.copyWithAlpha(0.5f),
                                cornerRadius = 24.dp
                            )
                            .padding(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            add(
                                Icon(
                                    icon = item.icon,
                                    tint = CNColor.Primary,
                                    size = 64.dp
                                )
                            )
                            add(Text(item.title, style = TextStyle.H4, fontWeight = FontWeight.Bold))
                            add(Text(item.subtitle, style = TextStyle.BodyMedium, color = CNColor.Gray, textAlign = TextAlign.Center))
                            add(Text(item.price, style = TextStyle.H4, color = CNColor.Primary, fontWeight = FontWeight.ExtraBold))
                        }
                    }
                }

                // Quantity Stepper
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = CNShape.RoundedCorner(14.dp),
                        elevation = 1.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            add(Text("Select Order Quantity", style = TextStyle.BodyLarge, fontWeight = FontWeight.Medium))
                            add(
                                Stepper(
                                    value = viewModel.quantity,
                                    onValueChange = { viewModel.quantity = it },
                                    range = 1.0..10.0,
                                    step = 1.0,
                                    label = "Units",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            )
                        }
                    }
                }

                // Checkout Button
                item {
                    Button(
                        onClick = {
                            viewModel.proceedToCheckout()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .background(CNColor.Primary, CNShape.RoundedCorner(14.dp))
                            .haptic(CNHapticType.Medium)
                    ) {
                        Text("Proceed to Checkout ➔", color = CNColor.White, style = TextStyle.Button)
                    }
                }
            }
        }

        // =====================================================================
        // Screen 3: Checkout Summary
        // =====================================================================
        composable(
            route = "checkout",
            title = "Order Checkout",
            navBarStyle = CNNavBarStyle.LiquidGlass
        ) {
            val item = viewModel.catalogItems.find { it.id == viewModel.selectedItemId } ?: viewModel.catalogItems.first()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = CNShape.RoundedCorner(16.dp),
                        elevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            add(Text("Order Summary", style = TextStyle.H5, fontWeight = FontWeight.Bold))
                            add(
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    add(Text("${item.title} (x${viewModel.quantity.toInt()})", style = TextStyle.BodyMedium))
                                    add(Text(item.price, style = TextStyle.BodyMedium, fontWeight = FontWeight.Bold))
                                }
                            )
                        }
                    }
                }

                // Delivery Date Picker
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = CNShape.RoundedCorner(14.dp),
                        elevation = 1.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            add(Text("Desired Delivery Date", style = TextStyle.BodyLarge, fontWeight = FontWeight.Medium))
                            add(
                                DatePicker(
                                    timestampMs = viewModel.deliveryDateMs,
                                    onDateChange = { viewModel.deliveryDateMs = it },
                                    title = "Delivery Date",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            )
                        }
                    }
                }

                // Special Instructions Field
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        add(Text("Delivery Instructions", style = TextStyle.LabelLarge, fontWeight = FontWeight.SemiBold))
                        add(
                            OutlinedTextField(
                                value = viewModel.customerNotes,
                                onValueChange = { viewModel.customerNotes = it },
                                placeholder = "e.g. Leave at front door or ring bell",
                                modifier = Modifier.fillMaxWidth()
                            )
                        )
                    }
                }

                // Confirm Order Button
                item {
                    Button(
                        onClick = {
                            viewModel.confirmOrder()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .background(CNColor.Success, CNShape.RoundedCorner(14.dp))
                            .haptic(CNHapticType.Success)
                    ) {
                        Text("Confirm & Pay Order", color = CNColor.White, style = TextStyle.Button)
                    }
                }
            }
        }

        // =====================================================================
        // Screen 4: Success & Pop Back to Root
        // =====================================================================
        composable(
            route = "success",
            title = "Order Confirmed 🎉",
            navBarStyle = CNNavBarStyle.LiquidGlass,
            showBackButton = false
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                add(
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .liquidGlass(
                                blurRadius = 30.dp,
                                tint = CNColor.Success.copyWithAlpha(0.18f),
                                borderHighlight = CNColor.Success.copyWithAlpha(0.5f),
                                cornerRadius = 24.dp
                            )
                            .padding(24.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            add(
                                Icon(
                                    icon = "checkmark.seal.fill",
                                    tint = CNColor.Success,
                                    size = 60.dp
                                )
                            )
                            add(Text("Order Placed Successfully!", style = TextStyle.H4, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center))
                            add(
                                Text(
                                    "Your multi-screen navigation flow completed seamlessly via Kotlin NavigationViewModel.",
                                    style = TextStyle.BodyMedium,
                                    color = CNColor.Gray,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                )

                add(Spacer(modifier = Modifier.height(24.dp)))

                add(
                    Button(
                        onClick = {
                            viewModel.returnToCatalog()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .background(CNColor.Primary, CNShape.RoundedCorner(14.dp))
                            .haptic(CNHapticType.Light)
                    ) {
                        Text("Back to Store Catalog", color = CNColor.White, style = TextStyle.Button)
                    }
                )
            }
        }
    }
}
