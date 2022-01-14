package dev.baseio.libjetcalendar.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*


@Parcelize
class JetMonth(val startDate: LocalDate, val endDate: LocalDate) : Parcelable {
  fun name(): String {
    return startDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
  }


  companion object {
    fun current(date: LocalDate = LocalDate.now()): JetMonth {
      val startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth())
      val endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth())
      return JetMonth(startOfMonth, endOfMonth)
    }
  }
}

fun JetMonth.weeks(firstDayOfWeek: DayOfWeek): List<JetWeek> {
  val currentYearMonth: YearMonth = YearMonth.of(this.endDate.year, this.endDate.monthValue)
  val weeks = currentYearMonth.atEndOfMonth()[WeekFields.SUNDAY_START.weekOfMonth()]
  val monthWeeks = mutableListOf<JetWeek>()
  monthWeeks.add(JetWeek.current(this@weeks.startDate,isFirstWeek = true, dayOfWeek = firstDayOfWeek))
  while (monthWeeks.size != weeks) {
    monthWeeks.add(monthWeeks.last().nextWeek(isFirstWeek = false))
  }
  return monthWeeks
}

