package dev.baseio.libjetcalendar.monthly

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.baseio.libjetcalendar.JetMonth
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.weeks

@Composable
fun JetCalendarMonthlyView(jetMonth: JetMonth = JetMonth.current()) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight(),
    verticalArrangement = Arrangement.SpaceAround,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(text = jetMonth.name())
    jetMonth.weeks().forEach { week ->
      JetCalendarWeekView(modifier = Modifier.fillMaxWidth(), week = week)
    }
  }
}