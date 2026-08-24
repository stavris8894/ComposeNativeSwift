package com.composenative.demo

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
 * Full Multi-Screen Navigation Demo with Liquid Glass Navigation Bar.
 */
class NavigationDemoScreen : CNScreen() {
    val navController = rememberNavController()

    // Screen State
    var selectedItemId by mutableStateOf("vision-pro")
    var quantity by mutableStateOf(1.0)
    var deliveryDateMs by mutableStateOf(1735689600000L) // Jan 1, 2025
    var customerNotes by mutableStateOf("")

    private val catalogItems = listOf(
        TechItem("vision-pro", "Apple Vision Pro 2", "Spatial computing headset with M4 silicon", "$3,499", "eyeglasses", "Flagship"),
        TechItem("macbook-ultra", "MacBook Pro Ultra 16\"", "M4 Max 128GB unified memory", "$4,199", "laptopcomputer", "Pro Performance"),
        TechItem("iphone-pro", "iPhone 17 Pro Titanium", "A19 Pro chip with tetraprism periscope", "$1,199", "iphone", "Bestseller"),
        TechItem("watch-ultra", "Apple Watch Ultra 3", "MicroLED display with satellite SOS", "$799", "applewatch", "Adventure")
    )

    init {
        registerState(navController.run { mutableStateOf(0) })
        navController.addStateListener { notifyStateChanged() }
    }

    override fun build(): CNNode = NavHost(
        navController = navController,
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
                        text = "Tap any product to navigate into the detail screen with Liquid Glass navigation transitions.",
                        style = TextStyle.BodyMedium,
                        color = CNColor.Gray
                    )
                }

                items(catalogItems) { item ->
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
                                selectedItemId = item.id
                                navController.navigate(
                                    route = "details",
                                    arguments = mapOf("itemId" to item.id)
                                )
                            }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    icon = item.icon,
                                    tint = CNColor.Primary,
                                    size = 32.dp
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                    Text(item.title, style = TextStyle.BodyLarge, fontWeight = FontWeight.Bold)
                                    Text(item.subtitle, style = TextStyle.Caption, color = CNColor.Gray)
                                    Text(item.price, style = TextStyle.BodyMedium, color = CNColor.Primary, fontWeight = FontWeight.SemiBold)
                                }
                            }
                            Badge(
                                text = item.tag,
                                backgroundColor = CNColor.Primary.copyWithAlpha(0.15f),
                                contentColor = CNColor.Primary
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
            val item = catalogItems.find { it.id == selectedItemId } ?: catalogItems.first()

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
                            Icon(
                                icon = item.icon,
                                tint = CNColor.Primary,
                                size = 64.dp
                            )
                            Text(item.title, style = TextStyle.H4, fontWeight = FontWeight.Bold)
                            Text(item.subtitle, style = TextStyle.BodyMedium, color = CNColor.Gray, textAlign = TextAlign.Center)
                            Text(item.price, style = TextStyle.H4, color = CNColor.Primary, fontWeight = FontWeight.ExtraBold)
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
                            Text("Select Order Quantity", style = TextStyle.BodyLarge, fontWeight = FontWeight.Medium)
                            Stepper(
                                value = quantity,
                                onValueChange = { quantity = it },
                                range = 1.0..10.0,
                                step = 1.0,
                                label = "Units",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                // Checkout Button
                item {
                    Button(
                        onClick = {
                            navController.navigate("checkout")
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
            val item = catalogItems.find { it.id == selectedItemId } ?: catalogItems.first()

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
                            Text("Order Summary", style = TextStyle.H5, fontWeight = FontWeight.Bold)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${item.title} (x${quantity.toInt()})", style = TextStyle.BodyMedium)
                                Text(item.price, style = TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                            }
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
                            Text("Desired Delivery Date", style = TextStyle.BodyLarge, fontWeight = FontWeight.Medium)
                            DatePicker(
                                timestampMs = deliveryDateMs,
                                onDateChange = { deliveryDateMs = it },
                                title = "Delivery Date",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                // Special Instructions Field
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Delivery Instructions", style = TextStyle.LabelLarge, fontWeight = FontWeight.SemiBold)
                        OutlinedTextField(
                            value = customerNotes,
                            onValueChange = { customerNotes = it },
                            placeholder = "e.g. Leave at front door or ring bell",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Confirm Order Button
                item {
                    Button(
                        onClick = {
                            navController.navigate(
                                route = "success",
                                navOptions = CNNavOptions(launchSingleTop = true)
                            )
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
                        Icon(
                            icon = "checkmark.seal.fill",
                            tint = CNColor.Success,
                            size = 60.dp
                        )
                        Text("Order Placed Successfully!", style = TextStyle.H4, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Text(
                            "Your multi-screen navigation flow completed seamlessly with real-time backstack popping.",
                            style = TextStyle.BodyMedium,
                            color = CNColor.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        navController.popUpTo("catalog", inclusive = false)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(CNColor.Primary, CNShape.RoundedCorner(14.dp))
                        .haptic(CNHapticType.Light)
                ) {
                    Text("Back to Store Catalog", color = CNColor.White, style = TextStyle.Button)
                }
            }
        }
    }
}
