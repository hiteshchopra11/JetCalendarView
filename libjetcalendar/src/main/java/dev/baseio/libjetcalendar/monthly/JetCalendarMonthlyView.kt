package dev.baseio.libjetcalendar.monthly

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.data.JetMonth
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView
import dev.baseio.libjetcalendar.data.weeks

@Composable
fun JetCalendarMonthlyView(
  jetMonth: JetMonth = JetMonth.current(),
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight(),
    verticalArrangement = Arrangement.SpaceAround,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = jetMonth.name(),
      style = TextStyle(fontSize = 18.sp),
      modifier = Modifier.padding(8.dp)
    )
    jetMonth.weeks().forEach { week ->
      JetCalendarWeekView(modifier = Modifier.fillMaxWidth(), week = week, onDateSelected,selectedDates)
    }
  }
}