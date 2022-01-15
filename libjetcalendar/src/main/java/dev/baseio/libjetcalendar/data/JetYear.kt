package dev.baseio.libjetcalendar.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters
import java.util.*
import java.time.temporal.TemporalQueries.localDate


@Parcelize
class JetYear(val startDate: LocalDate, val endDate: LocalDate) : Parcelable, JetCalendarType() {
  fun currentMonthPosition(): Int {
    return YearMonth.now().monthValue - 1
  }

  companion object {
    fun current(date: LocalDate = LocalDate.now()): JetYear {
      val day: LocalDate = date.with(TemporalAdjusters.firstDayOfYear())
      val last: LocalDate = date.with(TemporalAdjusters.lastDayOfYear())
      return JetYear(day, last)
    }
  }
}

fun JetYear.months(): List<JetMonth> {
  val months = mutableListOf<JetMonth>()

  var startDateMonth = this.startDate.withDayOfMonth(1)
  var endDateMonth = this.startDate.withDayOfMonth(this.startDate.lengthOfMonth())

  var currentYear = this.startDate.year
  while (true) {
    months.add(JetMonth(startDateMonth, endDateMonth))

    startDateMonth = endDateMonth.plusDays(1)
    endDateMonth = startDateMonth.withDayOfMonth(startDateMonth.lengthOfMonth())
    if (endDateMonth.year > currentYear) {
      break
    }
    currentYear = endDateMonth.year
  }
  return months
}
