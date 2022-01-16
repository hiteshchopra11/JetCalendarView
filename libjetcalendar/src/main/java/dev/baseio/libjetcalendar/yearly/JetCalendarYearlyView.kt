package dev.baseio.libjetcalendar.yearly

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.paging.*
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import java.time.DayOfWeek

@Composable
fun JetCalendarYearlyView(
  startingYear: JetYear = JetYear.current(),
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  firstDayOfWeek: DayOfWeek,
) {
  val monthsPager = Pager(PagingConfig(12)) {
    JetPagingSource(startingYear.startDate)
  }
  val lazyPagingMonths = monthsPager.flow.collectAsLazyPagingItems()
  // affected by https://stackoverflow.com/questions/69739108/how-to-save-paging-state-of-lazycolumn-during-navigation-in-jetpack-compose
  val listState = rememberLazyListState(startingYear.currentMonthPosition())

  YearViewInternal(listState, lazyPagingMonths, onDateSelected, selectedDates, firstDayOfWeek)
}


@Composable
private fun YearViewInternal(
  listState: LazyListState,
  pagedMonths: LazyPagingItems<JetMonth>,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  firstDayOfWeek: DayOfWeek
) {
  when (pagedMonths.itemCount) {
    0 -> CircularProgressIndicator()
    else -> {
      LazyColumn(
        state = listState,
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight()
      ) {

        items(pagedMonths) { month ->
          JetCalendarMonthlyView(month!!, onDateSelected, selectedDates, firstDayOfWeek)
        }
      }
    }
  }

}


@Composable
private fun lazyListStateSaver(lazyListState: LazyListState) =
  listSaver<LazyListState, Int>(
    save = {
      listOf(
        lazyListState.firstVisibleItemIndex,
        lazyListState.firstVisibleItemScrollOffset
      )
    },
    restore = {
      LazyListState(
        firstVisibleItemIndex = it[0],
        firstVisibleItemScrollOffset = it[1]
      )
    }
  )
