package dev.baseio.libjetcalendar.yearly

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.*
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.baseio.libjetcalendar.data.*
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek

@Composable
fun JetCalendarYearlyView(
  startingYear: JetYear = JetYear.current(),
  onDateSelected: (JetDay) -> Unit,
  selectedDates: Set<JetDay>,
  firstDayOfWeek: DayOfWeek,
  monthsFlow: Flow<PagingData<JetMonth>>
) {
  val lazyPagingMonths = monthsFlow.collectAsLazyPagingItems()

  val lazyListState = LazyListState(
    startingYear.currentMonthPosition(),
  )
  val listState = rememberSaveable(saver = lazyListStateSaver(lazyListState)) {
    lazyListState
  }

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
  LazyColumn(
    state = listState
  ) {
    items(pagedMonths) { month ->
      JetCalendarMonthlyView(month!!, onDateSelected, selectedDates, firstDayOfWeek)
    }
    loadingStates(pagedMonths)
  }
}

private fun LazyListScope.loadingStates(pagedMonths: LazyPagingItems<JetMonth>) {
  pagedMonths.apply {
    when {
      loadState.refresh is
          LoadState.Loading -> {
        item { LoadingItem() }
      }
      loadState.append is
          LoadState.Loading -> {
        item { LoadingItem() }
      }
      loadState.refresh is
          LoadState.Error -> {
      }
      loadState.append is
          LoadState.Error -> {
      }
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
