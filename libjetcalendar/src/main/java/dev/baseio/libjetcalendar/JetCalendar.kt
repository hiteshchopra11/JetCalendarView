package dev.baseio.libjetcalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.biweekly.JetCalendarBiWeeklyView
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import dev.baseio.libjetcalendar.monthly.WeekNames
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.yearly.JetCalendarYearlyView
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

@Composable
fun JetCalendar(
  modifier: Modifier,
  viewType: JetViewType = JetViewType.YEARLY,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  today: LocalDate = LocalDate.now(),
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>
) {
  when (viewType) {
    JetViewType.MONTHLY -> JetCalendarMonthlyView(
      jetMonth = JetMonth.current(today, firstDayOfWeek),
      onDateSelected = onDateSelected,
      selectedDates = selectedDates,
    )
    JetViewType.WEEKLY -> Column {
      Column {
        val week = JetWeek.current(today, isFirstWeek = true, dayOfWeek = firstDayOfWeek)
        WeekNames(week = week)
        JetCalendarWeekView(
          modifier = modifier,
          week = week,
          onDateSelected = onDateSelected,
          selectedDates = selectedDates,
        )
      }
    }
    JetViewType.BI_WEEKLY -> JetCalendarBiWeeklyView(
      weekOne = JetWeek.current(today, isFirstWeek = true, dayOfWeek = firstDayOfWeek),
      onDateSelected,
      selectedDates = selectedDates,
    )
    JetViewType.YEARLY -> {
      val year = JetYear.current(today, firstDayOfWeek)
      JetCalendarYearlyView(
        startingYear = year,
        onDateSelected, selectedDates,
        firstDayOfWeek = firstDayOfWeek,
      )
    }
  }
}

