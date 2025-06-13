package com.huy.chess.navigation

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.huy.chess.R
import com.huy.chess.ui.dialog.playonline.PlayOnlineDialog
import com.huy.chess.ui.home.HomeScreen
import com.huy.chess.ui.moreoptions.MoreOptionsScreen
import com.huy.chess.ui.puzzle.PuzzleScreen
import com.huy.chess.ui.theme.Blue300
import com.huy.chess.ui.theme.Green300
import com.huy.chess.ui.theme.Orange300

sealed class BottomNavScreens(
     val icon: String,
     val label: Int,
     val selectedColor: Color,
     val route: TopLevelDestination,
) {
    data object Home : BottomNavScreens(
        icon = "\u0160",
        label = R.string.home_text,
        selectedColor = Green300,
        route = TopLevelDestination.Home
    )

    data object Puzzles : BottomNavScreens(
        icon = "\u03DE",
        label = R.string.puzzle_text,
        selectedColor = Orange300,
        route = TopLevelDestination.Puzzles
    )

//    data object Study : BottomNavScreens(
//        icon = "\u1F20",
//        label = R.string.learn_text,
//        selectedColor = Blue300,
//        route = TopLevelDestination.Study
//    )

    data object MoreOptions : BottomNavScreens(
        icon = "\u0074",
        label = R.string.more_options_text,
        selectedColor = Color.White,
        route = TopLevelDestination.MoreOptions
    )

    companion object {
        val items = listOf(Home, Puzzles, MoreOptions)
    }
}

fun NavGraphBuilder.bottomDestination(
    navController: NavController,
    navigatePlay: () -> Unit,
    navigateDailyPuzzle: () -> Unit,
    navigateSolvePuzzles: () -> Unit,
    navigateSettings: () -> Unit,
    navigateProfile: () -> Unit,
    navigateHistory: () -> Unit

) {
    composable<TopLevelDestination.Home> {
        HomeScreen(
            navigateToPlay = navigatePlay,
            navigateToHistory = navigateHistory,
            navigateToGameArchive = { },
            navigateToDailyPuzzle = navigateDailyPuzzle,
            navigateToBot = navigateHistory,
            navigateToPuzzle = {  },
            navigateToStudy = { },
            showPlayOnlineDialog = { navController.navigate(PlayOnline) }
        )
    }

    composable<TopLevelDestination.Puzzles> {
        PuzzleScreen(
            navigateSolvePuzzles = navigateSolvePuzzles,
            navigateDailyPuzzles = navigateDailyPuzzle,
            navigateSetupPuzzleRush = {}
        )
    }

    composable<TopLevelDestination.Study> {

    }

    composable<TopLevelDestination.MoreOptions> {
        MoreOptionsScreen(
            navigateSettings = navigateSettings,
            navigateProfile = navigateProfile
        )
    }
    dialog<PlayOnline>{
        PlayOnlineDialog(
            popBackStack = { navController.popBackStack() },
            navigateWaiting = navigatePlay
        )
    }

}

