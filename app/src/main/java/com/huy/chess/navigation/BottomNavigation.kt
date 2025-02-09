package com.huy.chess.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.huy.chess.R
import com.huy.chess.ui.home.HomeScreen
import com.huy.chess.ui.moreoptions.MoreOptionsScreen
import kotlinx.serialization.Serializable

@Serializable object Main

@Serializable
sealed class BottomNavScreens(
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    @Serializable
    data object Home : BottomNavScreens(
        icon = R.drawable.home_24px,
        label = R.string.home_text
    )

    @Serializable
    data object Puzzles : BottomNavScreens(
        icon = R.drawable.extension_24px,
        label = R.string.puzzle_text
    )

    @Serializable
    data object Study : BottomNavScreens(
        icon = R.drawable.school_24px,
        label = R.string.learn_text
    )

    @Serializable
    data object MoreOptions : BottomNavScreens(
        icon = R.drawable.format_list_bulleted_24px,
        label = R.string.more_options_text
    )

    companion object {
        val items = listOf(Home, Puzzles, Study, MoreOptions)
    }
}

fun NavGraphBuilder.bottomDestination(
    onNavigateToPlay: () -> Unit
) {
    navigation<Main>(startDestination = BottomNavScreens.Home) {
        composable<BottomNavScreens.Home> {
            HomeScreen {
                onNavigateToPlay()
            }
        }

        composable<BottomNavScreens.Puzzles> {

        }

        composable<BottomNavScreens.Study> {

        }

        composable<BottomNavScreens.MoreOptions> {
            MoreOptionsScreen()
        }
    }
}

