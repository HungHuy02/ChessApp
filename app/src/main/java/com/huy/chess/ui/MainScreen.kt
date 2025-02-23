package com.huy.chess.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.huy.chess.navigation.Login
import com.huy.chess.navigation.Main
import com.huy.chess.navigation.Play
import com.huy.chess.navigation.Register
import com.huy.chess.navigation.authDestination
import com.huy.chess.navigation.bottomDestination
import com.huy.chess.navigation.playDestination

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Main,
        modifier = Modifier.fillMaxSize()
    ) {
        bottomDestination(
            navController = navController,
            onNavigateToPlay = {
                navController.navigate(Play)
            },
            onNavigateToLogin = {
                navController.navigate(Login)
            },
            onNavigateToRegister = {
                navController.navigate(Register)
            }
        )
        authDestination(navController)
        playDestination(navController)
    }
}