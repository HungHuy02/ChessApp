package com.huy.chess.navigation

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
object Play
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