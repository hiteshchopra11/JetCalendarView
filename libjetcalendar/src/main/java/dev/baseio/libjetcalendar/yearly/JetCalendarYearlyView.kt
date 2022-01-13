package dev.baseio.libjetcalendar.yearly

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.baseio.libjetcalendar.data.JetMonth
import dev.baseio.libjetcalendar.data.JetYear
import dev.baseio.libjetcalendar.data.months
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun JetCalendarYearlyView(startingYear: JetYear = JetYear.current()) {

  val state = MutableStateFlow(listOf<JetMonth>())
  val months by state.collectAsState()
  LazyColumn {
    items(months) { month ->
      JetCalendarMonthlyView(month)
    }
  }

  LaunchedEffect(Unit) {
    this.launch {
      withContext(Dispatchers.Default) {
        state.value = startingYear.months()
      }
    }
  }

}

