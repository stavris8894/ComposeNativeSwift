package com.composenative.demo.viewmodels

import com.composenative.swift.core.CNViewModel

data class PostItem(
    val id: String,
    val author: String,
    val handle: String,
    val avatarUrl: String,
    val content: String,
    val tag: String,
    var likes: Int,
    var isLiked: Boolean = false
)

class FeedViewModel : CNViewModel() {
    val posts = mutableListOf(
        PostItem(
            id = "1",
            author = "Alex Rivers",
            handle = "@arivers",
            avatarUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100&auto=format&fit=crop&q=80",
            content = "ComposeNativeSwift is a game changer! Writing Kotlin Compose and getting 100% genuine SwiftUI views on iOS is incredible.",
            tag = "KMP",
            likes = 42
        ),
        PostItem(
            id = "2",
            author = "Sophia Chen",
            handle = "@sophiac",
            avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100&auto=format&fit=crop&q=80",
            content = "No Skiko canvas overhead, native iOS accessibility and VoiceOver work out of the box. Absolutely seamless.",
            tag = "SwiftUI",
            likes = 128
        ),
        PostItem(
            id = "3",
            author = "Marcus Vance",
            handle = "@mvance",
            avatarUrl = "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=100&auto=format&fit=crop&q=80",
            content = "Zero configuration on the Swift app side: just ComposeNativeView(screen: MyScreen()) in your SwiftUI view tree!",
            tag = "Architecture",
            likes = 89
        )
    )

    private var changeCount by mutableStateOf(0)

    fun toggleLike(post: PostItem) {
        if (post.isLiked) {
            post.likes--
            post.isLiked = false
        } else {
            post.likes++
            post.isLiked = true
        }
        changeCount++
    }
}
