package dev.baseio.libjetcalendar.biweekly

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.data.JetWeek
import dev.baseio.libjetcalendar.data.nextWeek

@Composable
fun JetCalendarBiWeeklyView(weekOne: JetWeek = JetWeek.current()) {
  Column {
    val nextWeek = weekOne.nextWeek()
    JetCalendarWeekView(modifier = Modifier, week = weekOne)
    JetCalendarWeekView(modifier = Modifier, week = nextWeek)
  }
}