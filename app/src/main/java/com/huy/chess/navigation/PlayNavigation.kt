package com.huy.chess.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.huy.chess.ui.changetime.ChangeTimeScreen
import com.huy.chess.ui.dialog.playoptions.PlayOptionsDialog
import com.huy.chess.ui.newgame.NewGameScreen
import com.huy.chess.ui.play.PlayScreen
import com.huy.chess.ui.setupbot.SetupBotScreen
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleScreen

fun NavGraphBuilder.playDestination(
    navController: NavController
) {
    navigation<Game>(startDestination = NewGame) {
        composable<NewGame> {
            NewGameScreen(
                navigateToChangeTime = { navController.navigate(ChangeTime) },
                navigateToSetupBot = { navController.navigate(SetupBot) },
                navigateToSetupTwoPeople = { navController.navigate(SetupTwoPeople) },
                popBackStack = { navController.popBackStack() },
                navigateToPlay = { navController.navigate(Play) }
            )
        }
        composable<ChangeTime> {
            ChangeTimeScreen(
                popBackStack = { navController.popBackStack() }
            )
        }
        composable<SetupBot> {
            SetupBotScreen(
                popBackStack = { navController.popBackStack() }
            )
        }
        composable<SetupTwoPeople> {
            SetupTwoPeopleScreen(
                popBackStack = { navController.popBackStack() }
            )
        }
        composable<Play> {
            PlayScreen(
                showPlayOptionsDialog = { navController.navigate(PlayOptions) },
                popBackStack = { navController.popBackStack() }
            )
        }
        dialog<PlayOptions>{
            PlayOptionsDialog(
                popBackStack = { navController.popBackStack() }
            )
        }
    }
}