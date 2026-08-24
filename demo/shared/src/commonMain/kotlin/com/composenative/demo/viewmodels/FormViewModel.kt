package com.composenative.demo.viewmodels

import com.composenative.swift.core.CNViewModel

class FormViewModel : CNViewModel() {
    var searchQuery by mutableStateOf("")
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var selectedRole by mutableStateOf("Mobile Architect")
    var isSubscribed by mutableStateOf(true)
    var experienceYears by mutableStateOf(5f)
    var teamSize by mutableStateOf(4.0)
    var rating by mutableStateOf(5)
    var birthDateMs by mutableStateOf(1072915200000L) // Jan 1, 2004
    var isSubmitted by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun submit() {
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

    fun reset() {
        name = ""
        email = ""
        password = ""
        errorMessage = ""
        isSubmitted = false
    }
}
