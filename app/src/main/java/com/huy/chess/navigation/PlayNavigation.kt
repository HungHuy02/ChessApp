package com.huy.chess.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.huy.chess.ui.changetime.ChangeTimeScreen
import com.huy.chess.ui.newgame.NewGameScreen
import com.huy.chess.ui.setupbot.SetupBotScreen
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleScreen

fun NavGraphBuilder.playDestination(
    navController: NavController
) {
    navigation<Play>(startDestination = NewGame) {
        composable<NewGame> {
            NewGameScreen(
                navigateToChangeTime = { navController.navigate(ChangeTime) },
                navigateToSetupBot = { navController.navigate(SetupBot) },
                navigateToSetupTwoPeople = { navController.navigate(SetupTwoPeople) }
            )
        }
        composable<ChangeTime> {
            ChangeTimeScreen()
        }
        composable<SetupBot> {
            SetupBotScreen()
        }
        composable<SetupTwoPeople> {
            SetupTwoPeopleScreen()
        }
    }
}