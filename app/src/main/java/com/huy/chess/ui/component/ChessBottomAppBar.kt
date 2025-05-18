package com.huy.chess.ui.component

import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.huy.chess.navigation.BottomNavScreens
import com.huy.chess.navigation.TopLevelDestination
import com.huy.chess.ui.theme.ChessGlyphFontFamily


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
                    Text(
                        text = navigationItem.icon,
                        fontFamily = ChessGlyphFontFamily,
                        fontSize = 24.sp
                    )
                },
                onClick = { onClick(navigationItem.route) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = navigationItem.selectedColor,
                    selectedTextColor = navigationItem.selectedColor
                )
            )
        }
    }
}