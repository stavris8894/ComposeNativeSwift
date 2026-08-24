package com.composenative.demo.viewmodels

import com.composenative.swift.core.CNViewModel

class ProfileViewModel : CNViewModel() {
    var isFollowing by mutableStateOf(false)
    var followerCount by mutableStateOf(1420)
    var name by mutableStateOf("Elena Rostova")
    var title by mutableStateOf("Staff Mobile Architect")
    var bio by mutableStateOf("Building ultra-fast cross-platform applications with Kotlin Multiplatform & SwiftUI native components.")

    fun toggleFollow() {
        if (isFollowing) {
            followerCount--
            isFollowing = false
        } else {
            followerCount++
            isFollowing = true
        }
    }
}
