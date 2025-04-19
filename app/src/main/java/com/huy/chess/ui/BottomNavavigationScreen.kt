package com.huy.chess.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.huy.chess.data.model.User
import com.huy.chess.navigation.TopLevelDestination
import com.huy.chess.navigation.bottomDestination
import com.huy.chess.ui.component.ChessBottomAppBar
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.viewmodel.BottomNavViewModel

@Composable
fun BottomNavigationScreen(
    viewModel: BottomNavViewModel = hiltViewModel(),
    navigatePlay: () -> Unit,
    navigateDailyPuzzle: () -> Unit,
    navigateLogin: () -> Unit,
    navigateRegister: () -> Unit,
    navigateFriends: () -> Unit,
    navigateProfile: () -> Unit,
    navigateNotification: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                BottomNavEffect.NavigateFriends -> navigateFriends()
                BottomNavEffect.NavigateLogin -> navigateLogin()
                BottomNavEffect.NavigateNotification -> navigateNotification()
                BottomNavEffect.NavigateProfile -> navigateProfile()
                BottomNavEffect.NavigateRegister -> navigateRegister()
            }
        }
    }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        topBar = {
            ChessTopAppBar(
                state = state,
                currentDestination = currentDestination,
                onClick = viewModel::sendAction
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
            bottomDestination(
                navigatePlay = navigatePlay,
                navigateDailyPuzzle = navigateDailyPuzzle
            )
        }
    }
}

data class BottomNavState(
    val user: User = User()
)

sealed class BottomNavAction {
    data object ClickedRegister: BottomNavAction()
    data object ClickedLogin: BottomNavAction()
    data object ClickedProfile: BottomNavAction()
    data object ClickedNotification: BottomNavAction()
    data object ClickedFriends: BottomNavAction()
}

sealed class BottomNavEffect {
    data object NavigateRegister: BottomNavEffect()
    data object NavigateLogin: BottomNavEffect()
    data object NavigateProfile: BottomNavEffect()
    data object NavigateNotification : BottomNavEffect()
    data object NavigateFriends: BottomNavEffect()
}