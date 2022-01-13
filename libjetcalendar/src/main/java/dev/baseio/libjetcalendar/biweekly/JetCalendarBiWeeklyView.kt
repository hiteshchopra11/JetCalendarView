package dev.baseio.libjetcalendar.biweekly

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.data.JetWeek
import dev.baseio.libjetcalendar.data.nextWeek
import java.util.*

@Composable
fun JetCalendarBiWeeklyView(weekOne: JetWeek = JetWeek.current(Date())) {
  Column {
    val nextWeek = weekOne.nextWeek()
    JetCalendarWeekView(modifier = Modifier, week = weekOne)
    JetCalendarWeekView(modifier = Modifier, week = nextWeek)
  }
}