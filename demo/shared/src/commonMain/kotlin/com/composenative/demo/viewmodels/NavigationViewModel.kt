package com.composenative.demo.viewmodels

import com.composenative.demo.TechItem
import com.composenative.swift.core.CNViewModel
import com.composenative.swift.navigation.CNNavController
import com.composenative.swift.navigation.CNNavOptions
import com.composenative.swift.navigation.rememberNavController

class NavigationViewModel : CNViewModel() {
    val navController: CNNavController = rememberNavController()

    var selectedItemId by mutableStateOf("vision-pro")
    var quantity by mutableStateOf(1.0)
    var deliveryDateMs by mutableStateOf(1735689600000L) // Jan 1, 2025
    var customerNotes by mutableStateOf("")

    val catalogItems = listOf(
        TechItem("vision-pro", "Apple Vision Pro 2", "Spatial computing headset with M4 silicon", "$3,499", "eyeglasses", "Flagship"),
        TechItem("macbook-ultra", "MacBook Pro Ultra 16\"", "M4 Max 128GB unified memory", "$4,199", "laptopcomputer", "Pro Performance"),
        TechItem("iphone-pro", "iPhone 17 Pro Titanium", "A19 Pro chip with tetraprism periscope", "$1,199", "iphone", "Bestseller"),
        TechItem("watch-ultra", "Apple Watch Ultra 3", "MicroLED display with satellite SOS", "$799", "applewatch", "Adventure")
    )

    init {
        navController.addStateListener { notifyStateChanged() }
    }

    fun selectItem(id: String) {
        selectedItemId = id
        navController.navigate("details", mapOf("itemId" to id))
    }

    fun proceedToCheckout() {
        navController.navigate("checkout")
    }

    fun confirmOrder() {
        navController.navigate("success", navOptions = CNNavOptions(launchSingleTop = true))
    }

    fun returnToCatalog() {
        navController.popUpTo("catalog", inclusive = false)
    }

    fun popBack() {
        navController.popBackStack()
    }
}
