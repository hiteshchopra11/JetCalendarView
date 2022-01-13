package dev.baseio.libjetcalendar.biweekly

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.data.JetWeek
import dev.baseio.libjetcalendar.data.nextWeek
import java.time.LocalDate
import java.util.*

@Composable
fun JetCalendarBiWeeklyView(
  weekOne: JetWeek = JetWeek.current(LocalDate.now()),
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>
) {
  Column {
    val nextWeek = weekOne.nextWeek()
    JetCalendarWeekView(
      modifier = Modifier,
      week = weekOne,
      onDateSelected = onDateSelected,
      selectedDates = selectedDates
    )
    JetCalendarWeekView(
      modifier = Modifier,
      week = nextWeek,
      onDateSelected = onDateSelected,
      selectedDates = selectedDates
    )
  }
}