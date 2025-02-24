package com.huy.chess.navigation

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.huy.chess.R
import com.huy.chess.ui.component.BaseScreen
import com.huy.chess.ui.home.HomeScreen
import com.huy.chess.ui.moreoptions.MoreOptionsScreen
import com.huy.chess.ui.puzzle.PuzzleScreen
import kotlinx.serialization.Serializable

@Serializable object Home
@Serializable object Puzzles
@Serializable object Study
@Serializable object MoreOptions

sealed class BottomNavScreens(
     val icon: Int,
     val label: Int
) {
    data object Home : BottomNavScreens(
        icon = R.drawable.home_24px,
        label = R.string.home_text
    )

    data object Puzzles : BottomNavScreens(
        icon = R.drawable.extension_24px,
        label = R.string.puzzle_text
    )

    data object Study : BottomNavScreens(
        icon = R.drawable.school_24px,
        label = R.string.learn_text
    )

    data object MoreOptions : BottomNavScreens(
        icon = R.drawable.format_list_bulleted_24px,
        label = R.string.more_options_text
    )

    companion object {
        val items = listOf(Home, Puzzles, Study, MoreOptions)
    }
}

fun NavGraphBuilder.bottomDestination(
    navController: NavController
) {
    composable<Home> {
        BaseScreen(
            navController = navController,
            title = stringResource(R.string.app_name),
            isHomeScreen = true,
            onLoginButtonClick = { navController.navigate(Login) },
            onRegisterButtonClick = { navController.navigate(Register) }
        ) {
            HomeScreen { navController.navigate(Play) }
        }
    }

    composable<Puzzles> {
        BaseScreen(
            navController = navController,
            title = stringResource(R.string.puzzle_text),
        ) {
            PuzzleScreen()
        }
    }

    composable<Study> {
        BaseScreen(
            navController = navController,
            title = stringResource(R.string.study_text),
        ) {

        }
    }

    composable<MoreOptions> {
        BaseScreen(
            navController = navController,
            title = stringResource(R.string.more_options_text),
        ) {
            MoreOptionsScreen()
        }
    }
}

