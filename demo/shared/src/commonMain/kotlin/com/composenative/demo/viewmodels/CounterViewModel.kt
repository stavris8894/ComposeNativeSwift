package com.composenative.demo.viewmodels

import com.composenative.swift.core.CNViewModel

class CounterViewModel : CNViewModel() {
    var count by mutableStateOf(0)
    var step by mutableStateOf(1)

    fun increment() {
        count += step
    }

    fun decrement() {
        count -= step
    }

    fun reset() {
        count = 0
    }

    fun updateStep(newStep: Int) {
        step = newStep.coerceIn(1, 10)
    }
}
