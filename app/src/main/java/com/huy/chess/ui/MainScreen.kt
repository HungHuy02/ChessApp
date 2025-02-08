package com.huy.chess.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.huy.chess.R
import com.huy.chess.designsystem.ChessTopAppBar
import com.huy.chess.navigation.BottomNavScreens
import com.huy.chess.navigation.Main
import com.huy.chess.navigation.Play
import com.huy.chess.navigation.authDestination
import com.huy.chess.navigation.bottomDestination
import com.huy.chess.navigation.playDestination

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
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavScreens.items.forEach { navigationItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route.equals(navigationItem::class.qualifiedName)
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
                            navController.navigate(navigationItem) {
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
            startDestination = Main,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            bottomDestination(
                onNavigateToPlay = {
                    navController.navigate(Play)
                }
            )
            authDestination(navController)
            playDestination(navController)
        }
    }
}