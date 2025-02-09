package com.huy.chess.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.huy.chess.navigation.Main
import com.huy.chess.navigation.Play
import com.huy.chess.navigation.authDestination
import com.huy.chess.navigation.bottomDestination
import com.huy.chess.navigation.playDestination

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Main,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = Modifier.fillMaxSize()
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