package com.huy.chess.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.huy.chess.ui.home.HomeScreen

@Composable
fun MainScreen() {
    Scaffold { paddingValues ->
        HomeScreen(modifier = Modifier.padding(paddingValues))
    }
}