package dev.baseio.libjetcalendar.weekly

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.data.JetWeek
import dev.baseio.libjetcalendar.data.dates
import java.time.DayOfWeek

@Composable
fun JetCalendarWeekView(
  modifier: Modifier,
  week: JetWeek,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
) {

  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {

    week.dates().forEach { date ->
      Box(
        modifier = Modifier
          .size(50.dp)
          .padding(2.dp)
          .clip(CircleShape)
          .clickable {
            if (date.isPartOfMonth) {
              onDateSelected(date)
            }
          }
          .background(bgColor(selectedDates, date)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = date.date.dayOfMonth.toString(),
          modifier = Modifier.padding(4.dp),
          style = TextStyle(
            color = textColor(selectedDates, date)
          )
        )
      }
    }
  }
}

@Composable
private fun textColor(selectedDates: Set<JetDay>, date: JetDay): Color {
  if (selectedDates.contains(date)) {
    return Color.White
  }
  return if (date.isPartOfMonth) {
    Color.Black
  } else Color.LightGray
}

@Composable
private fun bgColor(
  selectedDates: Set<JetDay>,
  date: JetDay
) = if (selectedDates.contains(date)) Color.Black else Color.White