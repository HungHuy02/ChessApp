package com.huy.chess.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.huy.chess.ui.dialog.endgame.EndGameDialog
import com.huy.chess.ui.dialog.playoptions.PlayOptionsDialog
import com.huy.chess.ui.onlinewaiting.OnlineWaitingScreen
import com.huy.chess.ui.playonline.PlayOnlineScreen

fun NavGraphBuilder.playOnlineDestination(
    navController: NavController
) {
    navigation<Online>(startDestination = OnlineWaiting) {
        composable<OnlineWaiting> {
            OnlineWaitingScreen(
                navigatePlayOnline = { navController.navigate(PlayOnline) },
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