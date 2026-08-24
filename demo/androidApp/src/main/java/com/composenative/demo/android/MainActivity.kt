package com.composenative.demo.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.composenative.demo.ShowcaseScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val showcaseScreen = ShowcaseScreen()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var renderTrigger by remember { mutableIntStateOf(0) }

                    DisposableEffect(showcaseScreen) {
                        showcaseScreen.addListener {
                            renderTrigger++
                        }
                        onDispose {
                            showcaseScreen.onDispose()
                        }
                    }

                    // On Android, hosts the shared showcase tree
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "ComposeNative Android Host - Tab: ${showcaseScreen.selectedTab.name}",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
