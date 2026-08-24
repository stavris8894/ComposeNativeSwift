package com.composenative.demo

import com.composenative.demo.viewmodels.FeedViewModel
import com.composenative.swift.*
import com.composenative.swift.components.*
import com.composenative.swift.core.*

/**
 * Feed Screen using FeedViewModel in Kotlin Common.
 */
class FeedScreen(
    viewModel: FeedViewModel = FeedViewModel()
) : CNScreenWithViewModel<FeedViewModel>(viewModel) {

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
            items(viewModel.posts) { post ->
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
                                onClick = { viewModel.toggleLike(post) },
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
