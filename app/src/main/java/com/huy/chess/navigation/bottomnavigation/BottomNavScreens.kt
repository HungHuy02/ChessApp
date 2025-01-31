package com.huy.chess.navigation.bottomnavigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.huy.chess.R

sealed class BottomNavScreens(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    data object Home : BottomNavScreens(
        route = "home_route",
        icon = R.drawable.home_24px,
        label = R.string.home_text
    )

    data object Puzzles : BottomNavScreens(
        route = "puzzles_route",
        icon = R.drawable.extension_24px,
        label = R.string.puzzle_text
    )

    data object Study : BottomNavScreens(
        route = "study_route",
        icon = R.drawable.school_24px,
        label = R.string.learn_text
    )

    data object MoreOptions : BottomNavScreens(
        route = "options_routes",
        icon = R.drawable.format_list_bulleted_24px,
        label = R.string.more_options_text
    )
}
