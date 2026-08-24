package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Snackbar composable matching Material 3 Snackbar.
 */
fun Snackbar(
    message: String,
    modifier: CNModifier = CNModifier.None,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    isVisible: Boolean = true
): CNSnackbarNode = CNSnackbarNode(
    modifier = modifier,
    message = message,
    actionLabel = actionLabel,
    onAction = onAction,
    isVisible = isVisible
)
