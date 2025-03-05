package com.huy.chess.ui.component

import android.util.Log
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.huy.chess.navigation.BottomNavScreens
import com.huy.chess.navigation.Home
import com.huy.chess.navigation.MoreOptions
import com.huy.chess.navigation.Puzzles
import com.huy.chess.navigation.Study

@Composable
fun ChessBottomAppBar(
    navController: NavController
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        val navBackStackEntry by rememberUpdatedState(newValue = navController.currentBackStackEntry)
        val currentDestination = navBackStackEntry?.destination

        BottomNavScreens.items.forEach { navigationItem ->
            Log.e("tag", "test")

            val screen: Any = when (navigationItem) {
                BottomNavScreens.Home -> Home
                BottomNavScreens.MoreOptions -> MoreOptions
                BottomNavScreens.Puzzles -> Puzzles
                BottomNavScreens.Study -> Study
            }
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route.equals(screen::class.qualifiedName)
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
                    navController.navigate(screen) {
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