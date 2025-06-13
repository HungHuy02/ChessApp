package com.huy.chess.navigation

import com.huy.chess.utils.enums.GameResult
import kotlinx.serialization.Serializable

@Serializable
object Login
@Serializable
object Register
@Serializable
object RegisterWay
@Serializable
object EmailInput
@Serializable
object PasswordInput
@Serializable
object ProfileSetup
@Serializable
object Game
@Serializable
object NewGame
@Serializable
object ChangeTime
@Serializable
object SetupBot
@Serializable
object SetupTwoPeople
@Serializable
object RegisterDialog
@Serializable
object Main
@Serializable
object Profile
@Serializable
object GameArchive
@Serializable
object Friends
@Serializable
object Language
@Serializable
object EditProfile
@Serializable
object PlayOptions
@Serializable
object Play
@Serializable
object SelectDate
@Serializable
object Welcome
@Serializable
object DailyPuzzle
@Serializable
data class EndGame(val gameResult: GameResult)
@Serializable
object PlayOnline
@Serializable
object SolvePuzzles
@Serializable
data class PlayBot(val level: Int)
@Serializable
data object OnlineWaiting
@Serializable
data object Online
@Serializable
data object History

sealed class TopLevelDestination {
    @Serializable
    data object Home : TopLevelDestination()

    @Serializable
    data object Puzzles : TopLevelDestination()

    @Serializable
    data object Study : TopLevelDestination()

    @Serializable
    data object MoreOptions : TopLevelDestination()
}