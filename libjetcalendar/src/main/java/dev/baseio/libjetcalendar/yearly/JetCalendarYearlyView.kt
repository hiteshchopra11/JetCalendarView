package dev.baseio.libjetcalendar.yearly

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.DayOfWeek


@Composable
fun JetCalendarYearlyView(
  startingYear: JetYear = JetYear.current(),
  onDateSelected: (JetDay) -> Unit,
  onNextMonthsRequested: (JetMonth?) -> Unit,
  selectedDates: Set<JetDay>,
  firstDayOfWeek: DayOfWeek,
  months: List<JetMonth>
) {
  val state by rememberSaveable(stateSaver = monthsListSaver()) {
    mutableStateOf(months)
  }

  val lazyListState = LazyListState(
    startingYear.currentMonthPosition(),
  )
  val listState = rememberSaveable(saver = lazyListStateSaver(lazyListState)) {
    lazyListState
  }

  YearViewInternal(
    listState,
    state,
    onDateSelected,
    selectedDates,
    firstDayOfWeek,
    onNextMonthsRequested
  )
}

@Composable
private fun monthsListSaver() = listSaver<List<JetMonth>, Any>(save = {
  it
}, restore = {
  it.map { month -> month as JetMonth }
})


@Composable
private fun YearViewInternal(
  listState: LazyListState,
  pagedMonths: List<JetMonth>,
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  firstDayOfWeek: DayOfWeek,
  onNextMonthsRequested: (JetMonth?) -> Unit
) {

  val loadMore = remember {
    derivedStateOf {
      val layoutInfo = listState.layoutInfo
      val totalItemsNumber = layoutInfo.totalItemsCount
      val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

      lastVisibleItemIndex > (totalItemsNumber -  2)
    }
  }

  LaunchedEffect(loadMore) {
    snapshotFlow { loadMore.value }
      .distinctUntilChanged().collect {
        onNextMonthsRequested(pagedMonths.lastOrNull())
      }
  }

  LazyColumn(
    state = listState,
    modifier = Modifier
  ) {
    items(pagedMonths) { month ->
      JetCalendarMonthlyView(month, onDateSelected, selectedDates, firstDayOfWeek)
    }
  }
}


@Composable
fun LoadingItem() {
  CircularProgressIndicator(
    modifier =
    Modifier
      .testTag("ProgressBarItem")
      .fillMaxWidth()
      .padding(16.dp)
      .wrapContentWidth(
        Alignment.CenterHorizontally
      )
  )
}

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
