package com.composenative.demo

import com.composenative.demo.viewmodels.FormViewModel
import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

/**
 * Form and Inputs Showcase Screen using FormViewModel in Kotlin Common.
 */
class FormInputsScreen(
    viewModel: FormViewModel
) : CNScreenWithViewModel<FormViewModel>(viewModel) {
    constructor() : this(FormViewModel())

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
                    query = viewModel.searchQuery,
                    onQueryChange = { viewModel.searchQuery = it },
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
                        value = viewModel.name,
                        onValueChange = { viewModel.name = it },
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
                        value = viewModel.email,
                        onValueChange = { viewModel.email = it },
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
                        value = viewModel.password,
                        onValueChange = { viewModel.password = it },
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
                            Text(viewModel.selectedRole, style = TextStyle.BodySmall, color = CNColor.Primary)
                        }
                        DropdownMenu(title = viewModel.selectedRole) {
                            item(title = "Mobile Architect", icon = "laptopcomputer", onClick = { viewModel.selectedRole = "Mobile Architect" })
                            item(title = "iOS Lead", icon = "apple.logo", onClick = { viewModel.selectedRole = "iOS Lead" })
                            item(title = "Android Lead", icon = "antenna.radiowaves.left.and.right", onClick = { viewModel.selectedRole = "Android Lead" })
                            item(title = "Product Designer", icon = "paintbrush", onClick = { viewModel.selectedRole = "Product Designer" })
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
                            timestampMs = viewModel.birthDateMs,
                            onDateChange = { viewModel.birthDateMs = it },
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
                            value = viewModel.teamSize,
                            onValueChange = { viewModel.teamSize = it },
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
                            rating = viewModel.rating,
                            onRatingChange = { viewModel.rating = it },
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
                            checked = viewModel.isSubscribed,
                            onCheckedChange = { viewModel.isSubscribed = it }
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
                            Text("${viewModel.experienceYears.toInt()} Years", style = TextStyle.BodyLarge, color = CNColor.Primary, fontWeight = FontWeight.Bold)
                        }
                        Slider(
                            value = viewModel.experienceYears,
                            onValueChange = { viewModel.experienceYears = it },
                            valueRange = 0f..15f,
                            steps = 14
                        )
                    }
                }
            }

            // Error Feedback
            if (viewModel.errorMessage.isNotBlank()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = CNColor.Error.copyWithAlpha(0.12f),
                        border = CNBorder(1.dp, CNColor.Error)
                    ) {
                        Text(
                            text = viewModel.errorMessage,
                            color = CNColor.Error,
                            style = TextStyle.BodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }

            // Success Feedback
            if (viewModel.isSubmitted) {
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
                            Text("Welcome aboard, ${viewModel.name} (${viewModel.email})", style = TextStyle.BodyMedium)
                            Text("Role: ${viewModel.selectedRole} | Team: ${viewModel.teamSize.toInt()} devs | Rating: ${viewModel.rating}/5", style = TextStyle.Caption)
                        }
                    }
                }
            }

            // Submit Button
            item {
                Button(
                    onClick = { viewModel.submit() },
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
