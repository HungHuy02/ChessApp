package com.huy.chess.ui.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.component.VerticalHistoryList
import com.huy.chess.viewmodel.FriendsViewModel

val list = listOf(
    Triple(R.drawable.search, R.string.search_contacts_text, FriendsAction.ClickedSearchContact),
    Triple(R.drawable.facebook, R.string.facebook_friends_text, FriendsAction.ClickedFacebookFriends),
    Triple(R.drawable.invite, R.string.invite_friends_text, FriendsAction.ClickedInviteFriends),
    Triple(R.drawable.challengelink, R.string.send_challenge_link_text, FriendsAction.ClickedSendChallenge)
)

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                FriendsEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(onAction: (FriendsAction) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ChessTopAppBar(
            onClickBack = { onAction(FriendsAction.ClickedBack) },
            title = stringResource(R.string.friend_text),
            onAction = {
                Icon(
                    painter = painterResource(R.drawable.qr_code_24px),
                    contentDescription = "qr icon",
                    modifier = Modifier
                        .clickable {
                            onAction(FriendsAction.ClickedQR)
                        }
                )
            }
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            list.forEach {
                AppButton(
                    onClick = {
                        onAction(it.third)
                    },
                    painter = painterResource(it.first),
                    text = stringResource(it.second),
                    iconPosition = IconPosition.NEXT_TO_TEXT,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        text = stringResource(R.string.friends_search_hint_text)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.search_24px),
                        contentDescription = "search icon"
                    )
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.friend_text)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "9",
                    modifier = Modifier.background(Color.Gray)
                        .padding(2.dp)
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.leaderboard),
                    contentDescription = "leaderboard icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(R.string.leaderboard_text)
                )
            }
        }

        VerticalHistoryList()
    }
}