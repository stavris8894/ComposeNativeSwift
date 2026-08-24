package com.composenative.demo

import com.composenative.demo.viewmodels.ComponentsViewModel
import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

/**
 * Pagers, Glassmorphism, and Components Showcase using ComponentsViewModel in Kotlin Common.
 */
class ComponentsShowcaseScreen(
    viewModel: ComponentsViewModel = ComponentsViewModel()
) : CNScreenWithViewModel<ComponentsViewModel>(viewModel) {

    override fun build(): CNNode = Scaffold(
        topBar = TopAppBar(
            title = "Pagers & Glassmorphism",
            backgroundColor = CNColor.Surface
        ),
        content = LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Native Page Carousel", style = TextStyle.H4)
            }

            // Native Pager
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = CNShape.RoundedCorner(16.dp),
                    elevation = 2.dp
                ) {
                    HorizontalPager(
                        pageCount = viewModel.pagerItems.size,
                        currentPage = viewModel.currentPage,
                        onPageChange = { viewModel.currentPage = it },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        pages(viewModel.pagerItems) { item ->
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    icon = item.third,
                                    tint = CNColor.Primary,
                                    size = 36.dp
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(item.first, style = TextStyle.H5, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    item.second,
                                    style = TextStyle.BodyMedium,
                                    color = CNColor.Gray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            // Glassmorphism & Materials Showcase
            item {
                Text("Native Materials & Blur Vibrancy", style = TextStyle.H4)
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .material(CNMaterialType.UltraThin, CNShape.RoundedCorner(16.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Ultra-Thin Material Card", style = TextStyle.H5, fontWeight = FontWeight.Bold)
                        Text(
                            "This card uses iOS system UltraThinMaterial for genuine native glassmorphism and background vibrancy blur.",
                            style = TextStyle.BodyMedium,
                            color = CNColor.OnSurface
                        )
                    }
                }
            }

            // Snackbar Trigger & Display
            item {
                Button(
                    onClick = {
                        viewModel.triggerFeedback("Triggered native feedback at page ${viewModel.currentPage}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(CNColor.Secondary, CNShape.RoundedCorner(12.dp))
                        .haptic(CNHapticType.Light)
                ) {
                    Text("Trigger Native Feedback Snackbar", color = CNColor.White, style = TextStyle.Button)
                }
            }

            if (viewModel.showSnackbar) {
                item {
                    Snackbar(
                        message = viewModel.snackbarMessage,
                        actionLabel = "DISMISS",
                        onAction = { viewModel.dismissFeedback() }
                    )
                }
            }
        }
    )
}
