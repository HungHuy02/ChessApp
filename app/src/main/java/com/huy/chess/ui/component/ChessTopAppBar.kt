package com.huy.chess.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import coil3.compose.AsyncImage
import com.huy.chess.R
import com.huy.chess.navigation.TopLevelDestination
import com.huy.chess.ui.BottomNavAction
import com.huy.chess.ui.BottomNavState
import com.huy.chess.ui.theme.ChessGlyphFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChessTopAppBar(
    state: BottomNavState,
    currentDestination: NavDestination?,
    onClick: (BottomNavAction) -> Unit
) {
    val icon: Int?
    var title: String? = null
    var iconHeight: Dp = 32.dp
    when(currentDestination?.route) {
        TopLevelDestination.Home::class.qualifiedName -> {
            icon = R.drawable.logo
            iconHeight = 92.dp
        }
        TopLevelDestination.Study::class.qualifiedName -> {
            title = stringResource(R.string.puzzle_text)
            icon = R.drawable.puzzles
        }
        TopLevelDestination.Puzzles::class.qualifiedName -> {
            title = stringResource(R.string.study_text)
            icon = R.drawable.lessons
        }
        TopLevelDestination.MoreOptions::class.qualifiedName -> {
            icon = R.drawable.logo
            iconHeight = 108.dp
        }
        else -> {
            icon = null
            title = null
        }
    }
    CenterAlignedTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = "icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(iconHeight)
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
            if (currentDestination?.route.equals(TopLevelDestination.Home::class.qualifiedName)) {
                if(!state.isLogin) {
                    TextButton(onClick = { onClick(BottomNavAction.ClickedLogin) }) {
                        Text(
                            text = stringResource(R.string.login_text),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                } else {
                    AsyncImage(
                        model = state.user.avatar,
                        contentDescription = "image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(40.dp)
                            .clickable {
                                onClick(BottomNavAction.ClickedProfile)
                            }
                            .clip(MaterialTheme.shapes.small)
                    )
                }
            }
        },
        actions = {
            if (currentDestination?.route.equals(TopLevelDestination.Home::class.qualifiedName)) {
                if(!state.isLogin) {
                    Button(
                        onClick = { onClick(BottomNavAction.ClickedRegister) },
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
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_notifications),
                        contentDescription = "icon notification",
                        modifier = Modifier.size(24.dp)
                            .clickable {
                                onClick(BottomNavAction.ClickedNotification)
                            }
                    )
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(R.drawable.friends),
                        contentDescription = "icon friends",
                        Modifier.size(24.dp)
                            .clickable {
                                onClick(BottomNavAction.ClickedFriends)
                            }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun ChessTopAppBar(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    title: String? = null,
    isClose: Boolean = false,
    onClickBack: () -> Unit,
    onAction: @Composable RowScope.() -> Unit = {}
) {
    Box (
        modifier = modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily, fontSize = 20.sp)) {
                    append(if(isClose) "\u0042" else "\u005B")
                }
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickBack()
                }
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ) {
            icon?.let {
                Icon(
                    painter = it,
                    contentDescription = "icon",
                    tint = Color.Unspecified
                )
            }
            title?.let {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            onAction()
        }
    }
}
