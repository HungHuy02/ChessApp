package com.huy.chess.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.huy.chess.R
import com.huy.chess.navigation.NewGame
import com.huy.chess.navigation.SetupTwoPeople
import com.huy.chess.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChessTopAppBar(
    currentDestination: NavDestination?,
    onBackIconClick: () -> Unit = {},
    onRegisterButtonClick: () -> Unit = {},
    onLoginButtonClick: () -> Unit = {}
) {
    var icon: Int? = null
    var title: String? = null
    when(currentDestination?.route) {
        TopLevelDestination.Home::class.qualifiedName -> {
            title = stringResource(R.string.app_name)
        }
        TopLevelDestination.Study::class.qualifiedName -> {
            title = stringResource(R.string.puzzle_text)
            icon = R.drawable.school_24px
        }
        TopLevelDestination.Puzzles::class.qualifiedName -> {
            title = stringResource(R.string.study_text)
        }
        TopLevelDestination.MoreOptions::class.qualifiedName -> {
            title = stringResource(R.string.more_options_text)
        }
        NewGame::class.qualifiedName -> {
            title = stringResource(R.string.new_game_text)
        }
        SetupTwoPeople::class.qualifiedName -> {
            title = stringResource(R.string.play_with_friend_offline)
        }
        else -> {

        }
    }
    CenterAlignedTopAppBar(
        title = {
            Row {
                icon?.let {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = "icon"
                    )
                }
                title?.let {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        navigationIcon = {
            when (currentDestination?.route) {
                TopLevelDestination.Home::class.qualifiedName -> {
                    TextButton(onClick = onLoginButtonClick) {
                        Text(
                            text = stringResource(R.string.login_text),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                TopLevelDestination.Study::class.qualifiedName,
                TopLevelDestination.Puzzles::class.qualifiedName,
                TopLevelDestination.MoreOptions::class.qualifiedName -> {}

                else -> {
                    IconButton(
                        onClick = onBackIconClick,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back_24px),
                            contentDescription = "back icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        actions = {
            if (currentDestination?.route.equals(TopLevelDestination.Home::class.qualifiedName)) {
                Button(
                    onClick = onRegisterButtonClick,
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .height(32.dp)
                        .padding(end = 8.dp)
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                        .padding(top = 0.2.dp)
                        .background(Color.Blue, shape = MaterialTheme.shapes.small)
                        .padding(bottom = 0.8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.register_text),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}