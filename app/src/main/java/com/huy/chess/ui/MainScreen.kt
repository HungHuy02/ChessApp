package com.huy.chess.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.huy.chess.designsystem.ChessTopAppBar
import com.huy.chess.navigation.bottomnavigation.BottomNavScreens
import com.huy.chess.ui.home.HomeScreen
import com.huy.chess.R
import com.huy.chess.ui.theme.Gray900

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            ChessTopAppBar(
                title = stringResource(R.string.app_name),
                isHomeScreen = true
            )
        },
        bottomBar = {
            val listScreens = listOf(
                BottomNavScreens.Home,
                BottomNavScreens.Puzzles,
                BottomNavScreens.Study,
                BottomNavScreens.MoreOptions
            )
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listScreens.forEach { navigationItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route.equals(
                                navigationItem.route
                            )
                        } == true,
                        label = {
                            Text(text = stringResource(navigationItem.label))
                        },
                        icon = {
                            Icon(
                                painter = painterResource(navigationItem.icon),
                                contentDescription = "icon"
                            )
                        },
                        onClick = {
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(

                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavScreens.Home.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            composable(route = BottomNavScreens.Home.route) {
                HomeScreen()
            }

            composable(route = BottomNavScreens.Puzzles.route) {

            }

            composable(route = BottomNavScreens.Study.route) {

            }

            composable(route = BottomNavScreens.MoreOptions.route) {

            }
        }
    }
}