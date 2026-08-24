package com.composenative.demo.android

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composenative.swift.core.CNAlignment
import com.composenative.swift.core.CNArrangement
import com.composenative.swift.core.CNBackgroundModifier
import com.composenative.swift.core.CNBadgeNode
import com.composenative.swift.core.CNBoxNode
import com.composenative.swift.core.CNButtonNode
import com.composenative.swift.core.CNCardNode
import com.composenative.swift.core.CNChipNode
import com.composenative.swift.core.CNClickableModifier
import com.composenative.swift.core.CNClipModifier
import com.composenative.swift.core.CNColor
import com.composenative.swift.core.CNColumnNode
import com.composenative.swift.core.CNDatePickerNode
import com.composenative.swift.core.CNDividerNode
import com.composenative.swift.core.CNFillMaxHeightModifier
import com.composenative.swift.core.CNFillMaxSizeModifier
import com.composenative.swift.core.CNFillMaxWidthModifier
import com.composenative.swift.core.CNFontStyle
import com.composenative.swift.core.CNFontWeight
import com.composenative.swift.core.CNHeightModifier
import com.composenative.swift.core.CNIconNode
import com.composenative.swift.core.CNImageNode
import com.composenative.swift.core.CNLazyColumnNode
import com.composenative.swift.core.CNLazyRowNode
import com.composenative.swift.core.CNLiquidGlassNode
import com.composenative.swift.core.CNMenuNode
import com.composenative.swift.core.CNModifier
import com.composenative.swift.core.CNNavHostNode
import com.composenative.swift.core.CNNode
import com.composenative.swift.core.CNPaddingModifier
import com.composenative.swift.core.CNRatingBarNode
import com.composenative.swift.core.CNRowNode
import com.composenative.swift.core.CNScaffoldNode
import com.composenative.swift.core.CNScreen
import com.composenative.swift.core.CNSearchBarNode
import com.composenative.swift.core.CNShape
import com.composenative.swift.core.CNSizeModifier
import com.composenative.swift.core.CNSliderNode
import com.composenative.swift.core.CNSnackbarNode
import com.composenative.swift.core.CNSpacerNode
import com.composenative.swift.core.CNStateListener
import com.composenative.swift.core.CNStepperNode
import com.composenative.swift.core.CNSurfaceNode
import com.composenative.swift.core.CNSwitchNode
import com.composenative.swift.core.CNTextAlign
import com.composenative.swift.core.CNTextFieldNode
import com.composenative.swift.core.CNTextNode
import com.composenative.swift.core.CNWidthModifier

/**
 * Single entry point Composable to render any ComposeNative CNScreen in Jetpack Compose.
 */
@Composable
fun ComposeNativeView(screen: CNScreen, modifier: Modifier = Modifier) {
    var renderTrigger by remember { mutableIntStateOf(0) }

    DisposableEffect(screen) {
        val listener = CNStateListener {
            renderTrigger++
        }
        screen.addListener(listener)
        onDispose {
            screen.removeListener(listener)
            screen.onDispose()
        }
    }

    key(renderTrigger) {
        val rootNode = remember(screen, renderTrigger) { screen.render() }
        CNNodeRenderer(node = rootNode, modifier = modifier)
    }
}

/**
 * Recursive Jetpack Compose renderer for all ComposeNative CNNode types.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CNNodeRenderer(node: CNNode, modifier: Modifier = Modifier) {
    val nodeModifier = modifier.then(node.modifierElements.toComposeModifier())

    when (node) {
        is CNColumnNode -> {
            Column(
                modifier = nodeModifier,
                verticalArrangement = when (val arr = node.verticalArrangement) {
                    is CNArrangement.Center -> Arrangement.Center
                    is CNArrangement.Bottom -> Arrangement.Bottom
                    is CNArrangement.SpaceBetween -> Arrangement.SpaceBetween
                    is CNArrangement.SpaceEvenly -> Arrangement.SpaceEvenly
                    is CNArrangement.SpaceAround -> Arrangement.SpaceAround
                    is CNArrangement.SpacedBy -> Arrangement.spacedBy(arr.space.value.dp)
                    else -> Arrangement.Top
                },
                horizontalAlignment = when (node.horizontalAlignment) {
                    is CNAlignment.Horizontal.CenterHorizontally -> Alignment.CenterHorizontally
                    is CNAlignment.Horizontal.End -> Alignment.End
                    else -> Alignment.Start
                }
            ) {
                for (child in node.children) {
                    CNNodeRenderer(node = child)
                }
            }
        }

        is CNRowNode -> {
            Row(
                modifier = nodeModifier,
                horizontalArrangement = when (val arr = node.horizontalArrangement) {
                    is CNArrangement.Center -> Arrangement.Center
                    is CNArrangement.End -> Arrangement.End
                    is CNArrangement.SpaceBetween -> Arrangement.SpaceBetween
                    is CNArrangement.SpaceEvenly -> Arrangement.SpaceEvenly
                    is CNArrangement.SpaceAround -> Arrangement.SpaceAround
                    is CNArrangement.SpacedBy -> Arrangement.spacedBy(arr.space.value.dp)
                    else -> Arrangement.Start
                },
                verticalAlignment = when (node.verticalAlignment) {
                    is CNAlignment.Vertical.CenterVertically -> Alignment.CenterVertically
                    is CNAlignment.Vertical.Bottom -> Alignment.Bottom
                    else -> Alignment.Top
                }
            ) {
                for (child in node.children) {
                    CNNodeRenderer(node = child)
                }
            }
        }

        is CNBoxNode -> {
            Box(
                modifier = nodeModifier,
                contentAlignment = Alignment.Center
            ) {
                for (child in node.children) {
                    CNNodeRenderer(node = child)
                }
            }
        }

        is CNTextNode -> {
            Text(
                text = node.text,
                modifier = nodeModifier,
                color = node.style.color.toComposeColor(),
                fontSize = node.style.fontSize.value.sp,
                fontWeight = node.style.fontWeight.toComposeFontWeight(),
                fontStyle = if (node.style.fontStyle == CNFontStyle.Italic) FontStyle.Italic else FontStyle.Normal,
                textAlign = when (node.style.textAlign) {
                    CNTextAlign.Center -> TextAlign.Center
                    CNTextAlign.End, CNTextAlign.Right -> TextAlign.End
                    CNTextAlign.Justify -> TextAlign.Justify
                    else -> TextAlign.Start
                },
                maxLines = node.maxLines ?: Int.MAX_VALUE
            )
        }

        is CNButtonNode -> {
            Button(
                onClick = { node.onClick() },
                enabled = node.enabled,
                modifier = nodeModifier
            ) {
                CNNodeRenderer(node = node.content)
            }
        }

        is CNCardNode -> {
            Card(
                modifier = nodeModifier,
                shape = node.shape.toComposeShape(),
                colors = CardDefaults.cardColors(
                    containerColor = node.backgroundColor.toComposeColor()
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = node.elevation.value.dp
                )
            ) {
                CNNodeRenderer(node = node.content)
            }
        }

        is CNSurfaceNode -> {
            Surface(
                modifier = nodeModifier,
                shape = node.shape.toComposeShape(),
                color = node.color.toComposeColor(),
                shadowElevation = node.elevation.value.dp
            ) {
                CNNodeRenderer(node = node.content)
            }
        }

        is CNScaffoldNode -> {
            Scaffold(
                modifier = nodeModifier,
                topBar = {
                    node.topBar?.let { topBar ->
                        TopAppBar(
                            title = { Text(topBar.title) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = topBar.backgroundColor.toComposeColor(),
                                titleContentColor = topBar.contentColor.toComposeColor()
                            )
                        )
                    }
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    CNNodeRenderer(node = node.content)
                }
            }
        }

        is CNNavHostNode -> {
            Scaffold(
                modifier = nodeModifier,
                topBar = {
                    TopAppBar(
                        title = { Text(node.currentTitle) },
                        navigationIcon = {
                            if (node.showBackButton && node.backStackCount > 1) {
                                IconButton(onClick = { node.onPopBack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    CNNodeRenderer(node = node.content)
                }
            }
        }

        is CNLiquidGlassNode -> {
            Card(
                modifier = nodeModifier
                    .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(node.cornerRadius.value.dp)),
                shape = RoundedCornerShape(node.cornerRadius.value.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                CNNodeRenderer(node = node.content)
            }
        }

        is CNLazyColumnNode -> {
            LazyColumn(
                modifier = nodeModifier,
                contentPadding = PaddingValues(
                    start = node.contentPadding.start.value.dp,
                    top = node.contentPadding.top.value.dp,
                    end = node.contentPadding.end.value.dp,
                    bottom = node.contentPadding.bottom.value.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(node.children) { childNode ->
                    CNNodeRenderer(node = childNode)
                }
            }
        }

        is CNLazyRowNode -> {
            LazyRow(
                modifier = nodeModifier,
                contentPadding = PaddingValues(
                    start = node.contentPadding.start.value.dp,
                    top = node.contentPadding.top.value.dp,
                    end = node.contentPadding.end.value.dp,
                    bottom = node.contentPadding.bottom.value.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(node.children) { childNode ->
                    CNNodeRenderer(node = childNode)
                }
            }
        }

        is CNTextFieldNode -> {
            OutlinedTextField(
                value = node.value,
                onValueChange = { node.onValueChange(it) },
                placeholder = { Text(node.placeholder) },
                modifier = nodeModifier,
                singleLine = true
            )
        }

        is CNSwitchNode -> {
            Switch(
                checked = node.checked,
                onCheckedChange = { node.onCheckedChange(it) },
                modifier = nodeModifier
            )
        }

        is CNSliderNode -> {
            Slider(
                value = node.value,
                onValueChange = { node.onValueChange(it) },
                modifier = nodeModifier,
                valueRange = node.valueRange.start..node.valueRange.endInclusive,
                steps = node.steps
            )
        }

        is CNBadgeNode -> {
            SuggestionChip(
                onClick = {},
                label = { Text(node.text ?: "") },
                modifier = nodeModifier,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = node.backgroundColor.toComposeColor(),
                    labelColor = node.contentColor.toComposeColor()
                )
            )
        }

        is CNIconNode -> {
            Icon(
                imageVector = mapIconNameToImageVector(node.icon),
                contentDescription = null,
                tint = node.tint.toComposeColor(),
                modifier = nodeModifier
            )
        }

        is CNImageNode -> {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = nodeModifier
            )
        }

        is CNSpacerNode -> {
            Spacer(modifier = nodeModifier)
        }

        is CNDividerNode -> {
            HorizontalDivider(
                modifier = nodeModifier,
                thickness = node.thickness.value.dp,
                color = node.color.toComposeColor()
            )
        }

        is CNDatePickerNode -> {
            Card(
                modifier = nodeModifier,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = null)
                    Text(node.title ?: "Select Date", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        is CNStepperNode -> {
            Row(
                modifier = nodeModifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = {
                        val next = (node.value - node.step).coerceAtLeast(node.range.start)
                        node.onValueChange(next)
                    }
                ) {
                    Text("-", style = MaterialTheme.typography.headlineMedium)
                }
                Text("${node.value.toInt()} ${node.label}", style = MaterialTheme.typography.titleMedium)
                IconButton(
                    onClick = {
                        val next = (node.value + node.step).coerceAtMost(node.range.endInclusive)
                        node.onValueChange(next)
                    }
                ) {
                    Text("+", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }

        is CNRatingBarNode -> {
            Row(modifier = nodeModifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                for (i in 1..node.maxRating) {
                    val isSelected = i <= node.rating
                    IconButton(
                        onClick = { node.onRatingChange(i) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Text(
                            text = if (isSelected) "★" else "☆",
                            color = if (isSelected) node.activeColor.toComposeColor() else Color.Gray,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }

        is CNMenuNode -> {
            var expanded by remember { mutableStateOf(false) }
            Box(modifier = nodeModifier) {
                Button(onClick = { expanded = true }) {
                    Text(node.title)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    for (item in node.items) {
                        DropdownMenuItem(
                            text = { Text(item.title) },
                            onClick = {
                                expanded = false
                                item.onClick()
                            }
                        )
                    }
                }
            }
        }

        is CNSearchBarNode -> {
            OutlinedTextField(
                value = node.query,
                onValueChange = { node.onQueryChange(it) },
                placeholder = { Text(node.placeholder) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = nodeModifier.fillMaxWidth(),
                singleLine = true
            )
        }

        is CNChipNode -> {
            FilterChip(
                selected = node.selected,
                onClick = { node.onClick() },
                label = { Text(node.text) },
                modifier = nodeModifier
            )
        }

        is CNSnackbarNode -> {
            Snackbar(
                action = node.actionLabel?.let { label ->
                    {
                        TextButton(onClick = { node.onAction?.invoke() }) {
                            Text(label)
                        }
                    }
                },
                modifier = nodeModifier
            ) {
                Text(node.message)
            }
        }

        else -> {
            Box(modifier = nodeModifier)
        }
    }
}

// -------------------------------------------------------------------------
// Modifier and Style Mappers
// -------------------------------------------------------------------------

private fun List<CNModifier.Element>.toComposeModifier(): Modifier {
    var mod: Modifier = Modifier
    for (elem in this) {
        when (elem) {
            is CNPaddingModifier -> {
                mod = mod.padding(
                    start = elem.padding.start.value.dp,
                    top = elem.padding.top.value.dp,
                    end = elem.padding.end.value.dp,
                    bottom = elem.padding.bottom.value.dp
                )
            }
            is CNBackgroundModifier -> {
                mod = mod.background(
                    color = elem.color.toComposeColor(),
                    shape = elem.shape.toComposeShape()
                )
            }
            is CNFillMaxWidthModifier -> {
                mod = mod.fillMaxWidth()
            }
            is CNFillMaxSizeModifier -> {
                mod = mod.fillMaxSize()
            }
            is CNFillMaxHeightModifier -> {
                mod = mod.fillMaxHeight()
            }
            is CNHeightModifier -> {
                mod = mod.height(elem.height.value.dp)
            }
            is CNWidthModifier -> {
                mod = mod.width(elem.width.value.dp)
            }
            is CNSizeModifier -> {
                mod = mod.size(elem.width.value.dp, elem.height.value.dp)
            }
            is CNClickableModifier -> {
                if (elem.enabled) {
                    mod = mod.clickable { elem.onClick() }
                }
            }
            is CNClipModifier -> {
                mod = mod.clip(elem.shape.toComposeShape())
            }
            else -> {}
        }
    }
    return mod
}

private fun CNColor.toComposeColor(): Color = Color(
    red = red / 255f,
    green = green / 255f,
    blue = blue / 255f,
    alpha = alpha
)

private fun CNShape.toComposeShape(): Shape = when (this) {
    is CNShape.Circle -> CircleShape
    is CNShape.Capsule -> RoundedCornerShape(50)
    is CNShape.RoundedCorner -> RoundedCornerShape(
        topStart = topStart.value.dp,
        topEnd = topEnd.value.dp,
        bottomEnd = bottomEnd.value.dp,
        bottomStart = bottomStart.value.dp
    )
    else -> RectangleShape
}

private fun CNFontWeight.toComposeFontWeight(): FontWeight = when (this) {
    CNFontWeight.W100, CNFontWeight.Thin -> FontWeight.Thin
    CNFontWeight.W200, CNFontWeight.ExtraLight -> FontWeight.ExtraLight
    CNFontWeight.W300, CNFontWeight.Light -> FontWeight.Light
    CNFontWeight.W400, CNFontWeight.Normal -> FontWeight.Normal
    CNFontWeight.W500, CNFontWeight.Medium -> FontWeight.Medium
    CNFontWeight.W600, CNFontWeight.SemiBold -> FontWeight.SemiBold
    CNFontWeight.W700, CNFontWeight.Bold -> FontWeight.Bold
    CNFontWeight.W800, CNFontWeight.ExtraBold -> FontWeight.ExtraBold
    CNFontWeight.W900, CNFontWeight.Black -> FontWeight.Black
    else -> FontWeight.Normal
}

private fun mapIconNameToImageVector(name: String): ImageVector = when (name.lowercase()) {
    "eyeglasses", "vision" -> Icons.Default.Face
    "laptopcomputer", "laptop" -> Icons.Default.AccountBox
    "iphone", "phone" -> Icons.Default.Phone
    "applewatch", "watch" -> Icons.Default.Notifications
    "checkmark.seal.fill", "check" -> Icons.Default.CheckCircle
    "settings" -> Icons.Default.Settings
    "person", "profile" -> Icons.Default.Person
    "star" -> Icons.Default.Star
    else -> Icons.Default.Star
}
