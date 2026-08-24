package com.composenative.demo

import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

/**
 * Form and Inputs Showcase Screen written in Kotlin Compose.
 * Renders into native SwiftUI Form/TextField/Toggle/Slider components!
 */
class FormInputsScreen : CNScreen() {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isSubscribed by mutableStateOf(true)
    var experienceYears by mutableStateOf(3f)
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
            title = "User Registration Form",
            backgroundColor = CNColor.Surface
        ),
        content = LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Native Form Controls",
                    style = TextStyle.H4,
                    color = CNColor.OnBackground
                )
            }

            item {
                Text(
                    text = "Every control below is rendered natively using SwiftUI on iOS and Jetpack Compose on Android.",
                    style = TextStyle.BodyMedium,
                    color = CNColor.Gray
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
                            Text("Get weekly mobile tech updates", style = TextStyle.Caption)
                        }
                        Switch(
                            checked = isSubscribed,
                            onCheckedChange = { isSubscribed = it }
                        )
                    }
                }
            }

            // Slider
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
                ) {
                    Text("Submit Form", color = CNColor.White, style = TextStyle.Button)
                }
            }
        }
    )
}
