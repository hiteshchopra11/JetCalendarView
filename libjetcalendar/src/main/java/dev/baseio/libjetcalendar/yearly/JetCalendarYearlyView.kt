package dev.baseio.libjetcalendar.yearly

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.data.JetYear
import dev.baseio.libjetcalendar.data.months
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun JetCalendarYearlyView(
  startingYear: JetYear = JetYear.current(),
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  ) {
  val state = remember {
    MutableStateFlow(startingYear.months())
  }
  val months by state.collectAsState()
  LazyColumn(state = LazyListState(startingYear.currentMonth())) {
    items(months) { month ->
      JetCalendarMonthlyView(month, onDateSelected, selectedDates)
    }
  }

}

class Domain{
  class Joke{

  }
}

class Data{
  class Joke{

  }
}

