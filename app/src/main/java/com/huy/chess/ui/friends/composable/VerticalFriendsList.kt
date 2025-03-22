package com.huy.chess.ui.friends.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import com.huy.chess.R

@Composable
fun VerticalFriendsList(
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(5) {
            VerticalFriendsItem(
                image = "",
                name = "test",
                elo = 400
            )
        }
    }
}

@Composable
fun VerticalFriendsItem(
    modifier: Modifier = Modifier,
    image: String?,
    name: String,
    elo: Int,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
            .clickable {

            }.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val (imageRef, nameRef, eloRef, moreRef, playRef) = createRefs()
        if (image.isNullOrEmpty()) {
            Image(
                painter = painterResource(R.drawable.noavatar),
                contentDescription = "image",
                modifier = Modifier.constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
        } else {
            AsyncImage(
                model = image,
                contentDescription = "image",
                error = painterResource(R.drawable.noavatar),
                modifier = Modifier.constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
        }
        Text(
            text = name,
            modifier = Modifier.constrainAs(nameRef) {
                top.linkTo(imageRef.top)
                start.linkTo(imageRef.end, margin = 16.dp)
            }
        )
        Text(
            text = elo.toString(),
            modifier = Modifier.constrainAs(eloRef) {
                start.linkTo(nameRef.start)
                bottom.linkTo(imageRef.bottom)
            }
        )
        Icon(
            painter = painterResource(R.drawable.more_horiz_24px),
            contentDescription = "more icon",
            modifier = Modifier.constrainAs(moreRef) {
                end.linkTo(playRef.start, margin = 16.dp)
                top.linkTo(imageRef.top)
                bottom.linkTo(imageRef.bottom)
            }
        )
        FloatingActionButton(
            onClick = {},
            modifier = Modifier.size(48.dp)
                .constrainAs(playRef) {
                end.linkTo(parent.end)
                top.linkTo(imageRef.top)
                bottom.linkTo(imageRef.bottom)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.board_small),
                contentDescription = "icon"
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    VerticalFriendsList()
}
