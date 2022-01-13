package dev.baseio.libjetcalendar.biweekly

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.data.JetWeek
import dev.baseio.libjetcalendar.data.nextWeek

@Composable
fun JetCalendarBiWeeklyView() {
  Column {
    val week = JetWeek.current()
    val nextWeek = week.nextWeek()
    JetCalendarWeekView(modifier = Modifier, week = week)
    JetCalendarWeekView(modifier = Modifier, week = nextWeek)
  }
}