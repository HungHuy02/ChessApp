package com.huy.chess.ui.dialog.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.dialog.register.composables.ViewPagerContent
import com.huy.chess.ui.dialog.register.composables.ViewPagerIndicator

@Composable
fun RegisterDialog(
    navigateRegister: () -> Unit,
    popBackStack: () -> Unit
) {
    val listContent: List<Triple<Int, Int, Int>> = listOf(
        Triple(
            R.string.register_dialog_title_1,
            R.string.register_dialog_sub_title_1,
            R.string.register_dialog_description_1
        ),
        Triple(
            R.string.register_dialog_title_2,
            R.string.register_dialog_sub_title_2,
            R.string.register_dialog_description_2
        ),
        Triple(
            R.string.register_dialog_title_3,
            R.string.register_dialog_sub_title_3,
            R.string.register_dialog_description_3
        ),
        Triple(
            R.string.register_dialog_title_4,
            R.string.register_dialog_sub_title_4,
            R.string.register_dialog_description_4
        ),
        Triple(
            R.string.register_dialog_title_5,
            R.string.register_dialog_sub_title_5,
            R.string.register_dialog_description_5
        ),
    )
    val pagerState = rememberPagerState(pageCount = {
        listContent.size
    })
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.close_24px),
                    contentDescription = "close icon",
                    modifier = Modifier.align(Alignment.Start)
                        .clickable { popBackStack() }
                )
                Spacer(Modifier.height(80.dp))
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->
                    val (title, subTitle, description) = listContent[page]
                    ViewPagerContent(title, subTitle, description)
                }
                Spacer(Modifier.height(60.dp))
                ViewPagerIndicator(pagerState)
                Spacer(Modifier.weight(1f))
                AppButton(
                    onClick = navigateRegister,
                    text = stringResource(R.string.register_text),
                    iconPosition = IconPosition.NONE,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                TextButton(
                    onClick = popBackStack,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(R.string.not_today_text),
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}