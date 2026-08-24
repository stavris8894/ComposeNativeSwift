package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Standard TextField matching Jetpack Compose TextField.
 */
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: CNModifier = CNModifier.None,
    placeholder: String = "",
    label: String = "",
    isSecure: Boolean = false,
    keyboardType: CNKeyboardType = CNKeyboardType.Default,
    enabled: Boolean = true,
    isError: Boolean = false,
    singleLine: Boolean = true,
    leadingIcon: CNIconNode? = null,
    trailingIcon: CNIconNode? = null
): CNTextFieldNode = CNTextFieldNode(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    placeholder = placeholder,
    label = label,
    isSecure = isSecure,
    keyboardType = keyboardType,
    enabled = enabled,
    isError = isError,
    singleLine = singleLine,
    leadingIcon = leadingIcon,
    trailingIcon = trailingIcon
)

/**
 * Outlined TextField matching Jetpack Compose OutlinedTextField.
 */
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: CNModifier = CNModifier.None,
    placeholder: String = "",
    label: String = "",
    isSecure: Boolean = false,
    keyboardType: CNKeyboardType = CNKeyboardType.Default,
    enabled: Boolean = true,
    isError: Boolean = false,
    singleLine: Boolean = true,
    leadingIcon: CNIconNode? = null,
    trailingIcon: CNIconNode? = null
): CNTextFieldNode = CNTextFieldNode(
    modifier = modifier.border(CNBorder(1.dp, if (isError) CNColor.Error else CNColor.Gray, CNShape.RoundedCorner(8.dp))),
    value = value,
    onValueChange = onValueChange,
    placeholder = placeholder,
    label = label,
    isSecure = isSecure,
    keyboardType = keyboardType,
    enabled = enabled,
    isError = isError,
    singleLine = singleLine,
    leadingIcon = leadingIcon,
    trailingIcon = trailingIcon
)

/**
 * Switch / Toggle matching Jetpack Compose Switch.
 */
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true,
    tint: CNColor = CNColor.Primary
): CNSwitchNode = CNSwitchNode(
    modifier = modifier,
    checked = checked,
    onCheckedChange = onCheckedChange,
    enabled = enabled,
    tint = tint
)

/**
 * Slider matching Jetpack Compose Slider.
 */
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: CNModifier = CNModifier.None,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    enabled: Boolean = true,
    activeColor: CNColor = CNColor.Primary
): CNSliderNode = CNSliderNode(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    valueRange = valueRange,
    steps = steps,
    enabled = enabled,
    activeColor = activeColor
)

/**
 * Checkbox matching Jetpack Compose Checkbox.
 */
fun Checkbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: CNModifier = CNModifier.None,
    enabled: Boolean = true,
    checkedColor: CNColor = CNColor.Primary
): CNCheckboxNode = CNCheckboxNode(
    modifier = modifier,
    checked = checked,
    onCheckedChange = onCheckedChange,
    enabled = enabled,
    checkedColor = checkedColor
)
