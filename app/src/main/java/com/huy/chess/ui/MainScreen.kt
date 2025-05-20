package com.huy.chess.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.huy.chess.navigation.DailyPuzzle
import com.huy.chess.navigation.EditProfile
import com.huy.chess.navigation.Friends
import com.huy.chess.navigation.Game
import com.huy.chess.navigation.GameArchive
import com.huy.chess.navigation.Language
import com.huy.chess.navigation.Login
import com.huy.chess.navigation.Main
import com.huy.chess.navigation.Profile
import com.huy.chess.navigation.Register
import com.huy.chess.navigation.RegisterDialog
import com.huy.chess.navigation.SelectDate
import com.huy.chess.navigation.SolvePuzzles
import com.huy.chess.navigation.Welcome
import com.huy.chess.navigation.authDestination
import com.huy.chess.navigation.playDestination
import com.huy.chess.navigation.playOnlineDestination
import com.huy.chess.ui.component.FocusClearIme
import com.huy.chess.ui.dailypuzzle.DailyPuzzleScreen
import com.huy.chess.ui.dialog.register.RegisterDialog
import com.huy.chess.ui.editprofile.EditProfileScreen
import com.huy.chess.ui.friends.FriendsScreen
import com.huy.chess.ui.gamearchive.GameArchiveScreen
import com.huy.chess.ui.language.LanguageScreen
import com.huy.chess.ui.profile.ProfileScreen
import com.huy.chess.ui.selectdate.SelectDateScreen
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesScreen
import com.huy.chess.ui.welcome.WelcomeScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    FocusClearIme {
        NavHost(
            navController = navController,
            startDestination = Welcome
        ) {
            composable<Main> {
                BottomNavigationScreen(
                    navigatePlay = { navController.navigate(Game) },
                    navigateLogin = { navController.navigate(Login) },
                    navigateRegister = { navController.navigate(Register) },
                    navigateFriends = { navController.navigate(Friends) },
                    navigateProfile = { navController.navigate(Profile) },
                    navigateNotification = { },
                    navigateDailyPuzzle = { navController.navigate(DailyPuzzle) },
                    navigateSolvePuzzles = { navController.navigate(SolvePuzzles) }
                )
            }
            authDestination(navController)
            playDestination(navController)
            playOnlineDestination(navController)
            dialog<RegisterDialog> {
                RegisterDialog(
                    navigateRegister = { navController.navigate(Register) }
                ) {
                    navController.popBackStack()
                }
            }
            composable<Profile> {
                ProfileScreen(
                    navigateFriends = {},
                    navigateEditProfile = { navController.navigate(EditProfile) },
                    navigateGameArchive = { navController.navigate(GameArchive) },
                    popBackStack = { navController.popBackStack() }
                )
            }
            composable<GameArchive> {
                GameArchiveScreen(
                    popBackStack = { navController.popBackStack() }
                )
            }
            composable<Friends> {
                FriendsScreen(
                    popBackStack = { navController.popBackStack() }
                )
            }
            composable<Language> {
                LanguageScreen {
                    navController.popBackStack()
                }
            }
            composable<EditProfile> {
                EditProfileScreen(
                    navigateLanguage = { navController.navigate(Language) },
                    popBackStack = { navController.popBackStack() }
                )
            }
            composable<SelectDate> {
                SelectDateScreen(
                    popBackStack = { navController.popBackStack() }
                )
            }
            composable<Welcome> {
                WelcomeScreen(
                    navigateHome = { navController.navigate(Main) }
                )
            }
            composable<DailyPuzzle> {
                DailyPuzzleScreen(
                    navigateSelectDate = { navController.navigate(SelectDate) },
                    popBackStack = { navController.popBackStack() }
                )
            }
            composable<SolvePuzzles> {
                SolvePuzzlesScreen {
                    navController.popBackStack()
                }
            }
        }
    }
}