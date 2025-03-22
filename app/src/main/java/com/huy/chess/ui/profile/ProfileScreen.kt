package com.huy.chess.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.huy.chess.R
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.profile.composable.HorizontalFriendList
import com.huy.chess.ui.profile.composable.ProfileRowButton
import com.huy.chess.ui.component.VerticalHistoryList
import com.huy.chess.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateEditProfile: () -> Unit,
    navigateGameArchive: () -> Unit,
    navigateFriends: () -> Unit,
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                ProfileEffect.NavigateEditProfile -> navigateEditProfile()
                ProfileEffect.NavigateFriends -> navigateFriends()
                ProfileEffect.NavigateGameArchive -> navigateGameArchive()
                ProfileEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(onAction: (ProfileAction) -> Unit) {
    Column {
        ChessTopAppBar(
            onClickBack = {
                onAction(ProfileAction.ClickedBack)
            },
            action = {
                Icon(
                    painter = painterResource(R.drawable.qr_code_24px),
                    contentDescription = "qr icon",
                    modifier = Modifier.clickable {
                        onAction(ProfileAction.ClickedQR)
                    }
                )
                Icon(
                    painter = painterResource(R.drawable.more_vert_24px),
                    contentDescription = "more icon",
                    modifier = Modifier.clickable {
                        onAction(ProfileAction.ClickedMore)
                    }
                )
            }
        )
        ConstraintLayout(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            val (image, name, regionIcon, region, date) = createRefs()
            AsyncImage(
                model = "lll",
                contentDescription = "image",
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
            Text(
                text = "",
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(image.top)
                    start.linkTo(image.end, margin = 16.dp)
                }
            )
            Icon(
                painter = painterResource(R.drawable.vietnam),
                contentDescription = "region icon",
                modifier = Modifier.constrainAs(regionIcon) {
                    start.linkTo(name.start)
                    bottom.linkTo(image.bottom)
                }
            )
            Text(
                text = "Vietnam",
                modifier = Modifier.constrainAs(region) {
                    start.linkTo(regionIcon.end, margin = 8.dp)
                    top.linkTo(regionIcon.top)
                    bottom.linkTo(regionIcon.bottom)
                }
            )
            Text(
                text = "Joined",
                modifier = Modifier.constrainAs(date) {
                    start.linkTo(image.start)
                    top.linkTo(image.bottom, margin = 8.dp)
                }
            )
        }

        ProfileRowButton(
            text = "",
            number = 1
        ) {
            onAction(ProfileAction.ClickedRecentGames)
        }
        VerticalHistoryList()
        ProfileRowButton(
            text = "",
            number = 1
        ) {
            onAction(ProfileAction.ClickedFriends)
        }
        HorizontalFriendList()
    }
}