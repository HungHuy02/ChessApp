package com.huy.chess.navigation

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.huy.chess.R
import com.huy.chess.ui.changetime.ChangeTimeScreen
import com.huy.chess.ui.component.BaseScreen
import com.huy.chess.ui.newgame.NewGameScreen
import com.huy.chess.ui.setupbot.SetupBotScreen
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleScreen
import kotlinx.serialization.Serializable

@Serializable object Play
@Serializable object NewGame
@Serializable object ChangeTime
@Serializable object SetupBot
@Serializable object SetupTwoPeople

fun NavGraphBuilder.playDestination(
    navController: NavController
) {
    navigation<Play>(startDestination = NewGame) {
        composable<NewGame> {
            BaseScreen(
                title = stringResource(R.string.new_game_text),
                showBackIcon = true,
                onBackIconClick = { navController.popBackStack() }
            ) {
                NewGameScreen(
                    navigateToChangeTime = { navController.navigate(ChangeTime) },
                    navigateToSetupBot = { navController.navigate(SetupBot) },
                    navigateToSetupTwoPeople = { navController.navigate(SetupTwoPeople) }
                )
            }
        }
        composable<ChangeTime> {
            BaseScreen(
                showBackIcon = true,
                onBackIconClick = { navController.popBackStack() }
            ) {
                ChangeTimeScreen()
            }
        }
        composable<SetupBot> {
            BaseScreen(
                showBackIcon = true,
                onBackIconClick = { navController.popBackStack() }
            ) {
                SetupBotScreen()
            }
        }
        composable<SetupTwoPeople> {
            BaseScreen(
                title = stringResource(R.string.pass_and_play_text),
                showBackIcon = true,
                onBackIconClick = { navController.popBackStack() }
            ) {
                SetupTwoPeopleScreen()
            }
        }
    }
}