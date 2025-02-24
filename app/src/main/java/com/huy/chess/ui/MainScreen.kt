package com.huy.chess.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.huy.chess.navigation.Home
import com.huy.chess.navigation.authDestination
import com.huy.chess.navigation.bottomDestination
import com.huy.chess.navigation.playDestination

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = Modifier.fillMaxSize()
    ) {
        bottomDestination(navController)
        authDestination(navController)
        playDestination(navController)
    }
}