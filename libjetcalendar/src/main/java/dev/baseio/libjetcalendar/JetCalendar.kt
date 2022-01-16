package dev.baseio.libjetcalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.biweekly.JetCalendarBiWeeklyView
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import dev.baseio.libjetcalendar.monthly.WeekNames
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.yearly.JetCalendarYearlyView
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

@Composable
fun JetCalendar(
  modifier: Modifier,
  viewType: JetViewType = JetViewType.BI_WEEKLY,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  today: LocalDate = LocalDate.now(),
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>
) {
  when (viewType) {
    JetViewType.MONTHLY -> JetCalendarMonthlyView(
      jetMonth = JetMonth.current(today),
      onDateSelected = onDateSelected,
      selectedDates = selectedDates,
      firstDayOfWeek = firstDayOfWeek
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
      val year = JetYear.current(today)
      var stateFlow by rememberSaveable {
        val months = year.months()
        mutableStateOf(listOf<JetMonth>())
      }

      JetCalendarYearlyView(
        startingYear = year,
        onDateSelected,
        onNextMonthsRequested = {
          if (it == null) {
            stateFlow.addAll(year.months())
          } else {
            stateFlow.addAll(JetYear.current(it.endDate.plusDays(1)).months())
          }
        },
        selectedDates,
        firstDayOfWeek = firstDayOfWeek,
        stateFlow
      )
      LaunchedEffect(Unit) {
        this.launch {
          stateFlow = mutableListOf<JetMonth>().apply {
            addAll(year.months())
          }
        }
      }
    }
  }
}

