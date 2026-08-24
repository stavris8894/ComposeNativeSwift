package com.composenative.demo

import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

/**
 * Form and Inputs Showcase Screen written in Kotlin Compose.
 * Renders into native SwiftUI Form/TextField/Toggle/Slider/DatePicker/Stepper/Rating/Menu components!
 */
class FormInputsScreen : CNScreen() {
    var searchQuery by mutableStateOf("")
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var selectedRole by mutableStateOf("Mobile Architect")
    var isSubscribed by mutableStateOf(true)
    var experienceYears by mutableStateOf(5f)
    var teamSize by mutableStateOf(4.0)
    var rating by mutableStateOf(5)
    var birthDateMs by mutableStateOf(1072915200000L) // Jan 1, 2004
    var isSubmitted by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    private fun validateAndSubmit() {
        if (name.isBlank()) {
            errorMessage = "Name cannot be blank"
            isSubmitted = false
            return
        }
        if (!email.contains("@") || !email.contains(".")) {
            errorMessage = "Please enter a valid email"
            isSubmitted = false
            return
        }
        if (password.length < 6) {
            errorMessage = "Password must be at least 6 characters"
            isSubmitted = false
            return
        }
        errorMessage = ""
        isSubmitted = true
    }

    override fun build(): CNNode = Scaffold(
        topBar = TopAppBar(
            title = "Rich Native Controls Form",
            backgroundColor = CNColor.Surface
        ),
        content = LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Live Search Bar
            item {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    placeholder = "Search profiles or settings...",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Text(
                    text = "Rich Native Form Controls",
                    style = TextStyle.H4,
                    color = CNColor.OnBackground
                )
            }

            // Name Field
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Full Name", style = TextStyle.LabelLarge, fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = "e.g. John Appleseed",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Email Field
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Email Address", style = TextStyle.LabelLarge, fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "name@example.com",
                        keyboardType = KeyboardType.Email,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Password Field
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Password", style = TextStyle.LabelLarge, fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "At least 6 characters",
                        isSecure = true,
                        keyboardType = KeyboardType.Password,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Role Selector using Native Dropdown Menu
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(10.dp),
                    elevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Assigned Role", style = TextStyle.BodyLarge, fontWeight = FontWeight.Medium)
                            Text(selectedRole, style = TextStyle.BodySmall, color = CNColor.Primary)
                        }
                        DropdownMenu(title = selectedRole) {
                            item(title = "Mobile Architect", icon = "laptopcomputer", onClick = { selectedRole = "Mobile Architect" })
                            item(title = "iOS Lead", icon = "apple.logo", onClick = { selectedRole = "iOS Lead" })
                            item(title = "Android Lead", icon = "antenna.radiowaves.left.and.right", onClick = { selectedRole = "Android Lead" })
                            item(title = "Product Designer", icon = "paintbrush", onClick = { selectedRole = "Product Designer" })
                        }
                    }
                }
            }

            // Date of Birth Native Picker
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(10.dp),
                    elevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Date of Birth", style = TextStyle.BodyLarge, fontWeight = FontWeight.Medium)
                        DatePicker(
                            timestampMs = birthDateMs,
                            onDateChange = { birthDateMs = it },
                            title = "Birth Date",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Stepper (Team Size)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(10.dp),
                    elevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Engineering Team Size", style = TextStyle.BodyLarge, fontWeight = FontWeight.Medium)
                        Stepper(
                            value = teamSize,
                            onValueChange = { teamSize = it },
                            range = 1.0..50.0,
                            step = 1.0,
                            label = "Developers",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Star Rating Bar
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(10.dp),
                    elevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("KMP Satisfaction Rating", style = TextStyle.BodyLarge, fontWeight = FontWeight.Medium)
                        RatingBar(
                            rating = rating,
                            onRatingChange = { rating = it },
                            maxRating = 5
                        )
                    }
                }
            }

            // Newsletter Switch
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(10.dp),
                    elevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Subscribe to Newsletter", style = TextStyle.BodyLarge, fontWeight = FontWeight.Medium)
                            Text("Get weekly multiplatform updates", style = TextStyle.Caption)
                        }
                        Switch(
                            checked = isSubscribed,
                            onCheckedChange = { isSubscribed = it }
                        )
                    }
                }
            }

            // Experience Slider
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(10.dp),
                    elevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Experience Level", style = TextStyle.BodyLarge, fontWeight = FontWeight.Medium)
                            Text("${experienceYears.toInt()} Years", style = TextStyle.BodyLarge, color = CNColor.Primary, fontWeight = FontWeight.Bold)
                        }
                        Slider(
                            value = experienceYears,
                            onValueChange = { experienceYears = it },
                            valueRange = 0f..15f,
                            steps = 14
                        )
                    }
                }
            }

            // Error Feedback
            if (errorMessage.isNotBlank()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = CNColor.Error.copyWithAlpha(0.12f),
                        border = CNBorder(1.dp, CNColor.Error)
                    ) {
                        Text(
                            text = errorMessage,
                            color = CNColor.Error,
                            style = TextStyle.BodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }

            // Success Feedback
            if (isSubmitted) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = CNColor.Success.copyWithAlpha(0.12f),
                        border = CNBorder(1.dp, CNColor.Success)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Registration Successful!", color = CNColor.Success, fontWeight = FontWeight.Bold)
                            Text("Welcome aboard, $name ($email)", style = TextStyle.BodyMedium)
                            Text("Role: $selectedRole | Team: ${teamSize.toInt()} devs | Rating: $rating/5", style = TextStyle.Caption)
                        }
                    }
                }
            }

            // Submit Button
            item {
                Button(
                    onClick = { validateAndSubmit() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(CNColor.Primary, CNShape.RoundedCorner(12.dp))
                        .haptic(CNHapticType.Success)
                ) {
                    Text("Submit Form", color = CNColor.White, style = TextStyle.Button)
                }
            }
        }
    )
}
