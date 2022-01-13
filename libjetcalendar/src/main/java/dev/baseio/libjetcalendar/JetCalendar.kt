package dev.baseio.libjetcalendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.biweekly.JetCalendarBiWeeklyView
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.yearly.JetCalendarYearlyView
import java.time.LocalDate
import java.util.*

@Composable
fun JetCalendar(
  modifier: Modifier,
  viewType: JetViewType = JetViewType.YEARLY,
  today: LocalDate = LocalDate.now(),
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>
) {
  when (viewType) {
    JetViewType.MONTHLY -> JetCalendarMonthlyView(
      jetMonth = JetMonth.current(today),
      onDateSelected = onDateSelected,
      selectedDates = selectedDates
    )
    JetViewType.WEEKLY -> JetCalendarWeekView(
      modifier = modifier,
      week = JetWeek.current(today),
      onDateSelected = onDateSelected,
      selectedDates = selectedDates
    )
    JetViewType.BI_WEEKLY -> JetCalendarBiWeeklyView(
      weekOne = JetWeek.current(today),
      onDateSelected,
      selectedDates = selectedDates

    )
    JetViewType.YEARLY -> JetCalendarYearlyView(
      startingYear = JetYear.current(today),
      onDateSelected, selectedDates
    )
  }
}

