package com.composenative.demo

import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

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

/**
 * Feed Screen showcasing LazyColumn, Cards, Badges, Images, and dynamic state updates.
 */
class FeedScreen : CNScreen() {
    private val posts = mutableListOf(
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

    private var refreshTrigger by mutableStateOf(0)

    private fun toggleLike(post: PostItem) {
        if (post.isLiked) {
            post.likes--
            post.isLiked = false
        } else {
            post.likes++
            post.isLiked = true
        }
        refreshTrigger++
    }

    override fun build(): CNNode = Scaffold(
        topBar = TopAppBar(
            title = "Explore Feed",
            backgroundColor = CNColor.Surface
        ),
        content = LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(posts) { post ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CNShape.RoundedCorner(14.dp),
                    elevation = 2.dp,
                    backgroundColor = CNColor.Surface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Header: Avatar + Name + Tag
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    url = post.avatarUrl,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CNShape.Circle)
                                )
                                Column {
                                    Text(post.author, style = TextStyle.BodyLarge, fontWeight = FontWeight.Bold)
                                    Text(post.handle, style = TextStyle.Caption)
                                }
                            }

                            Badge(
                                text = post.tag,
                                backgroundColor = CNColor.Primary.copyWithAlpha(0.15f),
                                contentColor = CNColor.Primary
                            )
                        }

                        // Content
                        Text(
                            text = post.content,
                            style = TextStyle.BodyMedium,
                            lineHeight = 22.sp
                        )

                        Divider(color = CNColor.LightGray.copyWithAlpha(0.5f))

                        // Footer: Actions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { toggleLike(post) },
                                modifier = Modifier
                                    .background(
                                        if (post.isLiked) CNColor.Error.copyWithAlpha(0.12f) else CNColor.Transparent,
                                        CNShape.RoundedCorner(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        if (post.isLiked) "❤️" else "🤍",
                                        style = TextStyle.BodyMedium
                                    )
                                    Text(
                                        text = "${post.likes}",
                                        style = TextStyle.BodyMedium,
                                        color = if (post.isLiked) CNColor.Error else CNColor.OnSurface,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Text("Share", style = TextStyle.BodyMedium, color = CNColor.Primary)
                        }
                    }
                }
            }
        }
    )
}
