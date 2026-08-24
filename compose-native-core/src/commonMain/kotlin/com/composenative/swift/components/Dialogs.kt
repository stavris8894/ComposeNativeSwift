package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Alert Dialog composable matching Material 3 AlertDialog.
 */
fun AlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> CNNode,
    modifier: CNModifier = CNModifier.None,
    dismissButton: (() -> CNNode)? = null,
    icon: (() -> CNIconNode)? = null,
    title: (() -> CNNode)? = null,
    text: (() -> CNNode)? = null,
    isVisible: Boolean = true
): CNDialogNode {
    val titleNode = title?.invoke()
    val textNode = text?.invoke()
    val confirmNode = confirmButton()
    val dismissNode = dismissButton?.invoke()

    val titleStr = if (titleNode is CNTextNode) titleNode.text else "Alert"
    val textStr = if (textNode is CNTextNode) textNode.text else ""
    val confirmStr = if (confirmNode is CNTextNode) confirmNode.text else "Confirm"
    val dismissStr = if (dismissNode is CNTextNode) dismissNode.text else "Cancel"

    return CNDialogNode(
        modifier = modifier,
        isVisible = isVisible,
        onDismissRequest = onDismissRequest,
        title = titleStr,
        text = textStr,
        confirmButtonText = confirmStr,
        onConfirm = onDismissRequest,
        dismissButtonText = dismissStr,
        onDismiss = onDismissRequest
    )
}

/**
 * Modal Bottom Sheet matching Material 3 ModalBottomSheet.
 */
fun ModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: CNModifier = CNModifier.None,
    isVisible: Boolean = true,
    content: () -> CNNode
): CNBottomSheetNode = CNBottomSheetNode(
    modifier = modifier,
    isVisible = isVisible,
    onDismissRequest = onDismissRequest,
    content = content()
)
