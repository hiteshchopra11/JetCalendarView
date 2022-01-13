package dev.baseio.libjetcalendar.yearly

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import dev.baseio.libjetcalendar.data.JetYear
import dev.baseio.libjetcalendar.data.months
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView

@Composable
fun JetCalendarYearlyView(startingYear: JetYear = JetYear.current()) {
  LazyColumn {
    items(startingYear.months()) { month ->
      JetCalendarMonthlyView(month)
    }
  }
}

