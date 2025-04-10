package com.huy.chess.ui.selectdate.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huy.chess.R
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.core.yearMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.LazyThreadSafetyMode.NONE

object ContinuousSelectionHelper {
    fun getSelection(
        clickedDate: LocalDate,
        dateSelection: DateSelection,
    ): DateSelection {
        val (selectionStartDate, selectionEndDate) = dateSelection
        return if (selectionStartDate != null) {
            if (clickedDate < selectionStartDate || selectionEndDate != null) {
                DateSelection(startDate = clickedDate, endDate = null)
            } else if (clickedDate != selectionStartDate) {
                DateSelection(startDate = selectionStartDate, endDate = clickedDate)
            } else {
                DateSelection(startDate = clickedDate, endDate = null)
            }
        } else {
            DateSelection(startDate = clickedDate, endDate = null)
        }
    }

    fun isInDateBetweenSelection(
        inDate: LocalDate,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Boolean {
        if (startDate.yearMonth == endDate.yearMonth) return false
        if (inDate.yearMonth == startDate.yearMonth) return true
        val firstDateInThisMonth = inDate.yearMonth.nextMonth.atStartOfMonth()
        return firstDateInThisMonth in startDate..endDate && startDate != firstDateInThisMonth
    }

    fun isOutDateBetweenSelection(
        outDate: LocalDate,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Boolean {
        if (startDate.yearMonth == endDate.yearMonth) return false
        if (outDate.yearMonth == endDate.yearMonth) return true
        val lastDateInThisMonth = outDate.yearMonth.previousMonth.atEndOfMonth()
        return lastDateInThisMonth in startDate..endDate && endDate != lastDateInThisMonth
    }
}

data class DateSelection(val startDate: LocalDate? = null, val endDate: LocalDate? = null) {
    val daysBetween by lazy(NONE) {
        if (startDate == null || endDate == null) {
            null
        } else {
            ChronoUnit.DAYS.between(startDate, endDate)
        }
    }
}

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.ENGLISH)
}

fun DayOfWeek.displayText(uppercase: Boolean = false, narrow: Boolean = false): String {
    val style = if (narrow) TextStyle.NARROW else TextStyle.SHORT
    return getDisplayName(style, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

@Composable
fun CalendarPage() {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(3) }
    val endMonth = remember { currentMonth }
    val today = remember { LocalDate.now() }
    var selection by remember { mutableStateOf(DateSelection()) }
    val daysOfWeek = remember { daysOfWeek() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column {
            val state = rememberCalendarState(
                startMonth = startMonth,
                endMonth = endMonth,
                firstVisibleMonth = currentMonth,
                firstDayOfWeek = daysOfWeek.first(),
            )
            CalendarTop(

            )
            VerticalCalendar(
                state = state,
                contentPadding = PaddingValues(bottom = 100.dp),
                dayContent = { value ->
                    Day(
                        value,
                        today = today,
                        selection = selection,
                    ) { day ->
                        if (day.position == DayPosition.MonthDate &&
                            (day.date == today || day.date.isAfter(today))
                        ) {
                            selection = ContinuousSelectionHelper.getSelection(
                                clickedDate = day.date,
                                dateSelection = selection,
                            )
                        }
                    }
                },
                monthHeader = { month ->
                    MonthHeader(
                        calendarMonth = month,
                        daysOfWeek = daysOfWeek
                    )
                },
            )
        }
        CalendarBottom(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(Color.White)
                .align(Alignment.BottomCenter)
        )
    }
}


@Composable
private fun Day(
    day: CalendarDay,
    today: LocalDate,
    selection: DateSelection,
    onClick: (CalendarDay) -> Unit,
) {
    var textColor = Color.Black
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .clickable(
                enabled = day.position == DayPosition.MonthDate && day.date >= today,
                onClick = { onClick(day) },
            ),
//            .backgroundHighlight(
//                day = day,
//                today = today,
//                selection = selection,
//                selectionColor = selectionColor,
//                continuousSelectionColor = continuousSelectionColor,
//            ) { textColor = it },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    calendarMonth: CalendarMonth,
    daysOfWeek: List<DayOfWeek>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 8.dp),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            text = calendarMonth.yearMonth.displayText(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
        ) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    text = dayOfWeek.displayText(),
                    fontSize = 15.sp,
                )
            }
        }
    }
}

@Composable
private fun CalendarTop(
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 14.dp),
                text = buildAnnotatedString {
                    withStyle(SpanStyle()) {
                        appendLine(stringResource(R.string.select_date_text))
                    }
                    withStyle(SpanStyle()) {
                        append(stringResource(R.string.puzzles_get_harder_text))
                    }
                },
                textAlign = TextAlign.Center
            )

        }
    }
}

@Composable
private fun CalendarBottom(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.check_24px),
            contentDescription = "check icon",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.solve_puzzles_text)
        )
    }
}
