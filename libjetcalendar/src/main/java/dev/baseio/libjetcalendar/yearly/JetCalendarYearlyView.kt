package dev.baseio.libjetcalendar.yearly

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.*
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import java.time.DayOfWeek

@Composable
fun JetCalendarYearlyView(
  startingYear: JetYear,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  firstDayOfWeek: DayOfWeek,
) {
  val monthsPager = Pager(PagingConfig(12)) {
    JetPagingSource(startingYear.startDate, firstDayOfWeek)
  }
  val lazyPagingMonths = monthsPager.flow.collectAsLazyPagingItems()
  // affected by https://stackoverflow.com/questions/69739108/how-to-save-paging-state-of-lazycolumn-during-navigation-in-jetpack-compose
  val listState = rememberLazyListState(startingYear.currentMonthPosition())

  YearViewInternal(
    listState,
    lazyPagingMonths,
    onDateSelected,
    selectedDates
  )
}


@Composable
private fun YearViewInternal(
  listState: LazyListState,
  pagedMonths: LazyPagingItems<JetYear>,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>
) {
  when (pagedMonths.itemCount) {
    0 -> CircularProgressIndicator(color = Color.Black, modifier = Modifier.padding(8.dp))
    else -> {
      LazyColumn(
        state = listState,
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight()
      ) {
        calendarMonths(
          pagedMonths,
          onDateSelected,
          selectedDates
        )
      }
    }
  }

}

private fun LazyListScope.calendarMonths(
  pagedYears: LazyPagingItems<JetYear>,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
) {
  items(pagedYears) { year ->
    Box(
      modifier = Modifier.fillParentMaxWidth()
    ) {
      Column {
        year?.yearMonths?.forEach { month ->
          JetCalendarMonthlyView(month, onDateSelected, selectedDates)
        }
      }
    }
  }
}

private fun JetMonth?.year(): String {
  return "${this?.startDate?.year}"
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
