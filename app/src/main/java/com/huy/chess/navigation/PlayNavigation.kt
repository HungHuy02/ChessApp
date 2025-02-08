package com.huy.chess.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.huy.chess.ui.changetime.ChangeTimeScreen
import com.huy.chess.ui.newgame.NewGameScreen
import com.huy.chess.ui.setupbot.SetupBotScreen
import com.huy.chess.ui.setuptwopeople.SetupTwoPeopleScreen
import kotlinx.serialization.Serializable

@Serializable object Play
@Serializable object NewGame
@Serializable object ChangeTime
@Serializable object SetupBot
@Serializable object SetupTwoPeople

fun NavGraphBuilder.playDestination() {
    navigation<Play>(startDestination = NewGame) {
        composable<NewGame> {
            NewGameScreen()
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