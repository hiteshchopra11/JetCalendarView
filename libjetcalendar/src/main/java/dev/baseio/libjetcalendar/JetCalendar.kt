package dev.baseio.libjetcalendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.biweekly.JetCalendarBiWeeklyView
import dev.baseio.libjetcalendar.data.JetMonth
import dev.baseio.libjetcalendar.data.JetViewType
import dev.baseio.libjetcalendar.data.JetWeek
import dev.baseio.libjetcalendar.data.JetYear
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.yearly.JetCalendarYearlyView
import java.util.*

@Composable
fun JetCalendar(
  modifier: Modifier,
  viewType: JetViewType = JetViewType.YEARLY,
  today: Date = Date()
) {
  when (viewType) {
    JetViewType.MONTHLY -> JetCalendarMonthlyView(jetMonth = JetMonth.current(today))
    JetViewType.WEEKLY -> JetCalendarWeekView(modifier = modifier, week = JetWeek.current(today))
    JetViewType.BI_WEEKLY -> JetCalendarBiWeeklyView(weekOne = JetWeek.current(today))
    JetViewType.YEARLY -> JetCalendarYearlyView(startingYear = JetYear.current(today))
  }
}

