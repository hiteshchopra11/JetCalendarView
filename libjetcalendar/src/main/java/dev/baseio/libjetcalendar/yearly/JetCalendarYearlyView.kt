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
import java.time.DayOfWeek

@Composable
fun JetCalendarYearlyView(
  startingYear: JetYear = JetYear.current(),
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  firstDayOfWeek: DayOfWeek,
) {

  val state by rememberSaveable(stateSaver = monthsListSaver()) {
    mutableStateOf(startingYear.months())
  }
  val lazyListState = LazyListState(
    startingYear.currentMonthPosition(),
  )
  val listState = rememberSaveable(saver = lazyListStateSaver(lazyListState)) {
    lazyListState
  }

  LazyColumn(state = listState) {
    items(state) { month ->
      JetCalendarMonthlyView(month, onDateSelected, selectedDates, firstDayOfWeek)
    }
  }

}

@Composable
private fun monthsListSaver() = listSaver<List<JetMonth>, Any>(save = {
  it
}, restore = {
  it.map { month -> month as JetMonth }
})

@Composable
private fun lazyListStateSaver(lazyListState: LazyListState) =
  listSaver<LazyListState, Int>(
    save = { listOf(lazyListState.firstVisibleItemIndex) },
    restore = {
      LazyListState(
        firstVisibleItemIndex = it[0],
      )
    }
  )
