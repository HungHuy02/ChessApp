package com.huy.chess.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.huy.chess.ui.changetime.ChangeTimeScreen
import com.huy.chess.ui.dialog.endgame.EndGameDialog
import com.huy.chess.ui.dialog.playoptions.PlayOptionsDialog
import com.huy.chess.ui.newgame.NewGameScreen
import com.huy.chess.ui.play.PlayScreen
import com.huy.chess.ui.playbot.PlayBotScreen
import com.huy.chess.ui.playonline.PlayOnlineScreen
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
                navigateToPlayOnline = { navController.navigate(PlayOnline) }
            )
        }
        composable<ChangeTime> {
            ChangeTimeScreen(
                popBackStack = { navController.popBackStack() }
            )
        }
        composable<SetupBot> {
            SetupBotScreen(
                popBackStack = { navController.popBackStack() },
                navigatePlayBot = { navController.navigate(PlayBot(it)) }
            )
        }
        composable<PlayBot> {
            PlayBotScreen(
                showPlayOptionsDialog = { navController.navigate(PlayOptions) },
                showEndGameDialog = {navController.navigate(EndGame(it)) },
                popBackStack = { navController.popBackStack() }
            )
        }
        composable<SetupTwoPeople> {
            SetupTwoPeopleScreen(
                navigatePlay = { navController.navigate(Play) },
                popBackStack = { navController.popBackStack() }
            )
        }
        composable<Play> {
            PlayScreen(
                showEndGameDialog = {navController.navigate(EndGame(it)) },
                popBackStack = { navController.popBackStack() }
            )
        }
        composable<PlayOnline> {
            PlayOnlineScreen(
                showPlayOptionsDialog = { navController.navigate(PlayOptions) },
                showEndGameDialog = {navController.navigate(EndGame(it)) },
                popBackStack = { navController.popBackStack() }
            )
        }
        dialog<PlayOptions>{
            PlayOptionsDialog(
                popBackStack = { navController.popBackStack() }
            )
        }
        dialog<EndGame> {
            val route = it.toRoute<EndGame>()
            EndGameDialog(
                gameResult = route.gameResult,
                popBackStack = { navController.popBackStack() }
            )
        }
    }
}