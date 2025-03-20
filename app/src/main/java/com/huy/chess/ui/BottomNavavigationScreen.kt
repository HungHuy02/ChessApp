package com.huy.chess.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.huy.chess.navigation.TopLevelDestination
import com.huy.chess.navigation.bottomDestination
import com.huy.chess.ui.component.ChessBottomAppBar
import com.huy.chess.ui.component.ChessTopAppBar

@Composable
fun BottomNavigationScreen(
    navigatePlay: () -> Unit,
    navigateLogin: () -> Unit,
    navigateRegister: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        topBar = {
            ChessTopAppBar(
                currentDestination = currentDestination,
                onRegisterButtonClick = navigateRegister,
                onLoginButtonClick = navigateLogin
            )
        },
        bottomBar = {
            ChessBottomAppBar(currentDestination) {
                navController.navigate(it) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = TopLevelDestination.Home,
            modifier = Modifier.padding(it)
        ) {
            bottomDestination(navigatePlay)
        }
    }
}