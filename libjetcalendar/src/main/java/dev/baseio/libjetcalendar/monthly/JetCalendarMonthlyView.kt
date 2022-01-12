package dev.baseio.libjetcalendar.monthly

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.JetMonth
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.weeks

@Composable
fun JetCalendarMonthlyView(jetMonth: JetMonth = JetMonth.current()) {
  Column {
    jetMonth.weeks().forEach { week ->
      JetCalendarWeekView(modifier = Modifier, week = week)
    }
  }
}