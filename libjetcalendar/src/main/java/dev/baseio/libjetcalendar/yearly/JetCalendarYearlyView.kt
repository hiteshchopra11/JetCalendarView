package dev.baseio.libjetcalendar.yearly

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.data.JetMonth
import dev.baseio.libjetcalendar.data.JetYear
import dev.baseio.libjetcalendar.data.months
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView

@Composable
fun JetCalendarYearlyView(
  startingYear: JetYear = JetYear.current(),
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
) {

  val monthsSaver = listSaver<List<JetMonth>, Any>(save = {
    it
  }, restore = {
    it.map { month -> month as JetMonth }
  })

  val state by rememberSaveable(stateSaver = monthsSaver) {
    mutableStateOf(startingYear.months())
  }

  LazyColumn(state = LazyListState(startingYear.currentMonth())) {
    items(state) { month ->
      JetCalendarMonthlyView(month, onDateSelected, selectedDates)
    }
  }

}
