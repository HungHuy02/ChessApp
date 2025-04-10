package com.huy.chess.ui.selectdate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.ui.selectdate.composable.CalendarPage
import com.huy.chess.viewmodel.SelectDateViewModel

@Composable
fun SelectDateScreen(
    viewModel: SelectDateViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                SelectDateEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(
    onAction: (SelectDateAction) -> Unit
) {
    CalendarPage()
}