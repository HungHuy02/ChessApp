package com.huy.chess.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.huy.chess.R
import com.huy.chess.ui.home.HomeScreen
import com.huy.chess.ui.moreoptions.MoreOptionsScreen
import com.huy.chess.ui.puzzle.PuzzleScreen

sealed class BottomNavScreens(
     val icon: Int,
     val label: Int,
     val route: TopLevelDestination
) {
    data object Home : BottomNavScreens(
        icon = R.drawable.home_24px,
        label = R.string.home_text,
        route = TopLevelDestination.Home
    )

    data object Puzzles : BottomNavScreens(
        icon = R.drawable.extension_24px,
        label = R.string.puzzle_text,
        route = TopLevelDestination.Puzzles
    )

    data object Study : BottomNavScreens(
        icon = R.drawable.school_24px,
        label = R.string.learn_text,
        route = TopLevelDestination.Study
    )

    data object MoreOptions : BottomNavScreens(
        icon = R.drawable.format_list_bulleted_24px,
        label = R.string.more_options_text,
        route = TopLevelDestination.MoreOptions
    )

    companion object {
        val items = listOf(Home, Puzzles, Study, MoreOptions)
    }
}

fun NavGraphBuilder.bottomDestination(
    navigatePlay: () -> Unit
) {
    composable<TopLevelDestination.Home> {
        HomeScreen { navigatePlay() }
    }

    composable<TopLevelDestination.Puzzles> {
        PuzzleScreen()
    }

    composable<TopLevelDestination.Study> {

    }

    composable<TopLevelDestination.MoreOptions> {
        MoreOptionsScreen()
    }
}

