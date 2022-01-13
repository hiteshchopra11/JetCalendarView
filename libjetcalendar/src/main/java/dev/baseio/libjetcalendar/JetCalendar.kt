package dev.baseio.libjetcalendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.data.JetViewType
import java.time.DayOfWeek
import java.util.*

@Composable
fun JetCalendar(
  modifier: Modifier,
  viewType: JetViewType = JetViewType.MONTHLY,
  today: Date = Date(),
  firstDayOfWeek: DayOfWeek
) {
  JetCalendarView(modifier = modifier, firstDayOfWeek = firstDayOfWeek)

}

@Composable
fun JetCalendarView(modifier: Modifier, firstDayOfWeek: DayOfWeek) {

}
