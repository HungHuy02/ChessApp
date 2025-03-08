package com.huy.chess.ui.component

import android.util.Log
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.huy.chess.navigation.BottomNavScreens
import com.huy.chess.navigation.TopLevelDestination


@Composable
fun ChessBottomAppBar(
    currentDestination: NavDestination?,
    onClick: (TopLevelDestination) -> Unit
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        BottomNavScreens.items.forEach { navigationItem ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route.equals(navigationItem.route::class.qualifiedName)
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
                onClick = { onClick(navigationItem.route) },
                colors = NavigationBarItemDefaults.colors(

                )
            )
        }
    }
}