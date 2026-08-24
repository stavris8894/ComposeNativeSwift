package com.composenative.swift.components

import com.composenative.swift.core.*

/**
 * Native DatePicker composable matching Jetpack Compose DatePicker.
 */
fun DatePicker(
    timestampMs: Long,
    onDateChange: (Long) -> Unit,
    modifier: CNModifier = CNModifier.None,
    title: String = "Select Date",
    style: CNDatePickerStyle = CNDatePickerStyle.Compact,
    enabled: Boolean = true
): CNDatePickerNode = CNDatePickerNode(
    modifier = modifier,
    title = title,
    timestampMs = timestampMs,
    onDateChange = onDateChange,
    style = style,
    enabled = enabled
)

/**
 * Native TimePicker composable matching Jetpack Compose TimePicker.
 */
fun TimePicker(
    hour: Int,
    minute: Int,
    onTimeChange: (hour: Int, minute: Int) -> Unit,
    modifier: CNModifier = CNModifier.None,
    title: String = "Select Time",
    is24Hour: Boolean = true,
    enabled: Boolean = true
): CNTimePickerNode = CNTimePickerNode(
    modifier = modifier,
    title = title,
    hour = hour,
    minute = minute,
    onTimeChange = onTimeChange,
    is24Hour = is24Hour,
    enabled = enabled
)
