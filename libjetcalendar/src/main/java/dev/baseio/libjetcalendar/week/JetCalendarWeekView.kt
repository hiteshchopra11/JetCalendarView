package dev.baseio.libjetcalendar.week

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun JetCalendarWeekView(modifier: Modifier, week: JetWeek = JetWeek.current()) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
  ) {
    week.dates().forEach { date ->
      Box {
        Text(text = date.day.toString())
      }
    }
  }
}