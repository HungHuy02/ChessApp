package com.huy.chess.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
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
    val state = viewModel.state.collectAsState().value
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
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
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
                Spacer(Modifier.width(8.dp))
                Box {
                    Icon(
                        painter = painterResource(R.drawable.more_vert_24px),
                        contentDescription = "more icon",
                        modifier = Modifier.clickable {
                            onAction(ProfileAction.ClickedMore)
                        }
                    )
                    DropdownMenu(
                        expanded = state.showMore,
                        containerColor = MaterialTheme.colorScheme.background,
                        onDismissRequest = {
                            onAction(ProfileAction.ClickedMore)
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.edit_text),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            },
                            onClick = {
                                onAction(ProfileAction.ClickedEdit)
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.share_profile_text),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            },
                            onClick = {
                                onAction(ProfileAction.ClickedShareProfile)
                            }
                        )
                    }
                }

            }
        )
        ConstraintLayout(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            val (image, name, regionIcon, region, date) = createRefs()
            AsyncImage(
                model = state.user.avatar,
                contentDescription = "image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(60.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )
            Text(
                text = state.user.name ?: "",
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(image.top)
                    start.linkTo(image.end, margin = 16.dp)
                }
            )
            Icon(
                painter = painterResource(R.drawable.vietnam),
                contentDescription = "region icon",
                tint = Color.Unspecified,
                modifier = Modifier
                    .width(40.dp)
                    .constrainAs(regionIcon) {
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