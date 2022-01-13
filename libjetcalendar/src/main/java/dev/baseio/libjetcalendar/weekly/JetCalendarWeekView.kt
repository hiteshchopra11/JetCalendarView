package dev.baseio.libjetcalendar.weekly

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.baseio.libjetcalendar.data.JetWeek
import dev.baseio.libjetcalendar.data.dates
import java.util.*

@Composable
fun JetCalendarWeekView(modifier: Modifier, week: JetWeek = JetWeek.current(Date(),Date())) {
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    week.dates().forEach { date ->
      Box(
        modifier = Modifier
          .size(48.dp)
      ) {
        Text(
          text = date.day.toString(),
          modifier = Modifier.padding(4.dp),
          style = TextStyle(color = if (date.isIncludeMonth) Color.Black else Color.LightGray)
        )
      }
    }
  }
}